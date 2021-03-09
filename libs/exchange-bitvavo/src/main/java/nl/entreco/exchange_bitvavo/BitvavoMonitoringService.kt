package nl.entreco.exchange_bitvavo

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import nl.entreco.exchange_bitvavo.api.Bitvavo
import nl.entreco.exchange_bitvavo.api.PriceTicker
import nl.entreco.exchange_core.ExchangeMonitoringService
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicBoolean


/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class BitvavoMonitoringService : ExchangeMonitoringService {

    private val bitvavo = Bitvavo.builder()
        .withApiKey("see local.properties")
        .withSecret("see local.properties")
        .withDebugging()
        .build()

    private val monitoringStarted = AtomicBoolean(false)

    override suspend fun start(): BigDecimal {
        monitoringStarted.set(true)

        val remaining = bitvavo.rateLimitRemaining
        Log.i("WHOAH", "RemainingLimit: $remaining")
        return BigDecimal(currentPrice().price)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun monitor(): Flow<BigDecimal> {
        return bitvavo.subscriptionTicker("BTC-EUR")
            .filter { it.lastPrice.isNotEmpty() }
            .map { BigDecimal(it.lastPrice.toBigInteger()) }
    }

    override fun stop() {
        bitvavo.client.close()
        monitoringStarted.set(false)
    }

    private suspend fun currentPrice(): PriceTicker {
        return bitvavo.tickerPrice().firstOrNull { it.market == "BTC-EUR" } ?: PriceTicker(
            "None",
            "--"
        )
    }
}