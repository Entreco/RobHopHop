package nl.entreco.exchange_bitvavo

import android.util.Log
import com.njkim.reactivecrypto.core.common.model.currency.Currency
import com.njkim.reactivecrypto.core.common.model.currency.CurrencyPair
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import nl.entreco.exchange_bitvavo.api.Bitvavo
import nl.entreco.exchange_bitvavo.api.PriceTicker
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService
import nl.entreco.exchange_core.Monitor
import java.math.BigDecimal
import java.util.concurrent.atomic.AtomicBoolean

class BitvavoMonitoringService(
    private val exchange: Exchange
) : ExchangeMonitoringService {

    private val bitvavo = Bitvavo.builder()
        .withApiKey(BuildConfig.API_KEY)
        .withSecret(BuildConfig.API_SECRET)
        .withDebugging()
        .build()

    private val monitoringStarted = AtomicBoolean(false)

    override suspend fun start(): BigDecimal {
        monitoringStarted.set(true)

        val remaining = bitvavo.rateLimitRemaining
        Log.i("WHOAH", "RemainingLimit: $remaining")
        return BigDecimal(currentPrice()?.price ?: "0")
    }

    override suspend fun regular(currencies: List<CurrencyPair>): Flow<Monitor> {
        return bitvavo.subscriptionTicker("BTC-EUR")
            .filter { it.lastPrice.isNotEmpty() }
            .map { Monitor(exchange, BigDecimal(it.lastPrice.toBigInteger()), CurrencyPair(Currency.BTC, Currency("EUR"))) }
    }

    override fun stop() {
        bitvavo.client.close()
        monitoringStarted.set(false)
    }

    private suspend fun currentPrice(): PriceTicker? {
        return bitvavo.tickerPrice().firstOrNull { it.market == "BTC-EUR" }
    }
}