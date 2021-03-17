package nl.entreco.exchange_core

import com.njkim.reactivecrypto.core.common.model.currency.Currency
import com.njkim.reactivecrypto.core.common.model.currency.CurrencyPair
import com.njkim.reactivecrypto.core.common.model.order.TickData

data class Arbitrage(
    val market: String,
    val triples: Triple<Currency, Currency, Currency>,
    val fast: MutableMap<Currency, Float?> = mutableMapOf(
        triples.first to null,
        triples.second to null,
        triples.third to null
    )
) {

    // ETH-USDT :: 1832.70000000
    // ETH-BTC :: 0.03170900
    // BTC-USDT :: 57910.62000000

    operator fun plusAssign(tick: TickData) {
        when {
            tick.currencyPair.quoteCurrency == triples.second -> fast[triples.second] = tick.price.toFloat()
            tick.currencyPair.baseCurrency == triples.second -> fast[triples.third] = 1f / tick.price.toFloat()
            else -> fast[triples.first] = tick.price.toFloat()
        }
    }

    fun ratios(): Triple<Float, Float, Float> =
        Triple(fast[triples.first] ?: 1F, fast[triples.second] ?: 1F, fast[triples.third] ?: 1F)

    fun arbitrages(): Triple<String, String, String> =
        Triple(triples.first.symbol, triples.second.symbol, triples.third.symbol)

    fun isFilled(): Boolean = fast.filter { it.value != null }.size == 3

    fun reset(): Arbitrage = this.copy(market = market, triples = triples)

    val pairs: List<CurrencyPair> = listOf(
        CurrencyPair(triples.first, triples.second),
        CurrencyPair(triples.first, triples.third),
        CurrencyPair(triples.second, triples.third),
    )
}
