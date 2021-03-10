package nl.entreco.exchange_bitvavo.api

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.apache.commons.codec.binary.Hex
import java.util.concurrent.atomic.AtomicBoolean
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec


/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class Bitvavo internal constructor(
    builder: Builder
) {

    val apiKey: String = builder.key!!

    val apiSecret: String = builder.secret!!
    val restUrl: String = builder.restUrl
    val wsUrl: String = builder.wsUrl
    val debugging = builder.debugging
    val window = builder.accessWindow

    @Volatile
    var rateLimitRemaining: Int = 1000

    @Volatile
    var rateLimitReset: Long = 0
    private val rateLimitThreadStarted = AtomicBoolean(false)
    private val activatedSubscriptionTicker = AtomicBoolean(false)

    val client: HttpClient by lazy {
        HttpClient(CIO) {
            install(WebSockets)
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
    }

    suspend fun tickerPrice(options: List<Pair<String, String>> = emptyList()): List<PriceTicker> {
        val urlparameters = "?market=BTC-EUR"
        return publicRequest("$restUrl/ticker/price$urlparameters", "GET")
    }

    private suspend inline fun <reified T> publicRequest(url: String, method: String): List<T> {
        if (this.apiKey.isNotBlank()) {
            val response = client.get<HttpResponse>(url) {
                val timestamp = System.currentTimeMillis()
                val signature = createSignature(timestamp, method, url.replace(restUrl, ""))
                headers {
                    header("Bitvavo-Access-Key", apiKey)
                    header("Bitvavo-Access-Signature", signature)
                    header("Bitvavo-Access-Timestamp", "$timestamp")
                    header("Bitvavo-Access-Window", "$window")
                    header("Content-Type", "application/json")
                }
            }

            return when (response.status.value) {
                200 -> {
                    updateRateLimit(response.headers)
                    listOf(Json.decodeFromString<T>(response.readText()))
                }
                else -> {
                    val error = Json.decodeFromString<Error>(response.readText())
                    errorRateLimit(error)
                    emptyList()
                }
            }
        }
        return emptyList()
    }

    private suspend fun errorRateLimit(error: Error) {
        if (error.errorCode == 105) {
            rateLimitRemaining = 0
            val placeHolder = error.error.split(" at ")[1].replace(".", "")
            rateLimitReset = placeHolder.toLong()
            if (!rateLimitThreadStarted.get()) {
                rateLimitThreadStarted.set(true)
                delay(rateLimitReset - System.currentTimeMillis())
                rateLimitThreadStarted.set(false)
                if (System.currentTimeMillis() >= rateLimitReset) {
                    rateLimitRemaining = 1000
                }
            }
        }
    }

    private suspend fun updateRateLimit(headers: Headers) {
        val remainingHeader = headers["Bitvavo-Ratelimit-Remaining"]?.get(0)
        val resetHeader = headers["Bitvavo-Ratelimit-ResetAt"]?.get(0)
        if (remainingHeader != null) {
            rateLimitRemaining = remainingHeader.toInt()
        }
        if (resetHeader != null) {
            rateLimitReset = resetHeader.toLong()
            if (!rateLimitThreadStarted.get()) {
                rateLimitThreadStarted.set(true)
                delay(rateLimitReset - System.currentTimeMillis())
                rateLimitThreadStarted.set(false)
                if (System.currentTimeMillis() >= rateLimitReset) {
                    rateLimitRemaining = 1000
                }
            }
        }
    }

    private fun createSignature(timestamp: Long, method: String, endpoint: String): String {
        val result = "${timestamp}${method}/v2${endpoint}"
        val sha256 = Mac.getInstance("HmacSHA256")
        val secret = SecretKeySpec(apiSecret.toByteArray(Charsets.UTF_8), "HmacSHA256")
        sha256.init(secret)
        return String(Hex.encodeHex(sha256.doFinal(result.toByteArray(Charsets.UTF_8))))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    suspend fun subscriptionTicker(market: String): Flow<SubscriptionTicker> =
        flow<SubscriptionTicker> {
            val options = SubscriptionTickerOptions(
                listOf(Channel(listOf(market), "ticker")),
                "subscribe"
            )

            val stringOptions = Json { ignoreUnknownKeys = true }.encodeToString(
                SubscriptionTickerOptions.serializer(),
                options
            )
            Log.d("WOAH", "Options: $options")
            Log.d("WOAH", "Options: $stringOptions")

            val shouldbe =
                "{\"channels\":[{\"markets\":[\"BTC-EUR\"],\"name\":\"ticker\"}],\"action\":\"subscribe\"}"
            Log.d("WOAH", "Options: $shouldbe")

            activatedSubscriptionTicker.set(true)

            client.wss(wsUrl) {
                send(shouldbe)
                val confirmation = incoming.receive()
                Log.d("WOAH", "Subscription: ${(confirmation as? Frame.Text)?.readText()}")

                while (true) {
                    val frame = incoming.receive()
                    when (frame) {
                        is Frame.Text -> {
                            try {
                                val subscription: SubscriptionTicker =
                                    Json.decodeFromString<SubscriptionTicker>(frame.readText())
                                Log.d("WOAH", "Subscription: ${frame.readText()}")
                                emit(subscription)
                            } catch (oops: Exception) {
                                Log.d("WOAH", "Error: ${frame.readText()}")
                                Log.d("WOAH", "Error: $oops")
                            }
                        }
                    }
                }
            }
        }

    companion object {
        fun builder() = Builder()
    }
}

class Builder {
    internal var key: String? = null
    internal var secret: String? = null
    internal var accessWindow: Int = 10000
    internal var debugging: Boolean = false
    internal val restUrl = "https://api.bitvavo.com/v2"
    internal val wsUrl = "wss://ws.bitvavo.com/v2/"

    fun withApiKey(key: String) = apply {
        this.key = key
    }

    fun withSecret(secret: String) = apply {
        this.secret = secret
    }

    fun withAccessWindow(accessWindow: Int) = apply {
        this.accessWindow = accessWindow
    }

    fun withDebugging() = apply {
        debugging = true
    }

    fun build(): Bitvavo {
        requireNotNull(key) { "Must specify api key" }
        requireNotNull(secret) { "Must specify api secret" }
        return Bitvavo(this)
    }
}

@Serializable
data class PriceTicker(
    val market: String,
    val price: String,
)

@Serializable
data class Error(
    val errorCode: Int,
    val error: String,
)

@Serializable
data class SubscriptionTickerOptions(
    //{"channels":[{"markets":["BTC-EUR"]}],"action":"subscribe"}
    //{"channels":[{"markets":["BTC-EUR"],"name":"ticker"}],"action":"subscribe"}
    val channels: List<Channel>,
    val action: String,
)

@Serializable
data class Channel(
    val markets: List<String>,
    val name: String
)

@Serializable
data class SubscriptionTicker(
    val market: String,
    val bestAsk: String = "",
    val bestBidSize: String = "",
    val event: String,
    val bestBid: String = "",
    val bestAskSize: String = "",
    val lastPrice: String = "",
)