package nl.entreco.robhophop.main.arbitrage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.njkim.reactivecrypto.core.common.model.currency.Currency
import com.njkim.reactivecrypto.core.common.model.currency.Currency.Companion.ADA
import com.njkim.reactivecrypto.core.common.model.currency.Currency.Companion.BTC
import com.njkim.reactivecrypto.core.common.model.currency.Currency.Companion.ETH
import com.njkim.reactivecrypto.core.common.model.currency.Currency.Companion.USDT
import com.njkim.reactivecrypto.core.common.model.currency.CurrencyPair
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.entreco.exchange_core.ExchangeMonitoringService
import nl.entreco.robhophop.main.arbitrage.trades.triangular.TriangularTrade
import java.util.*
import javax.inject.Inject

class ArbitrageViewModel @Inject constructor(
    exchanges: Map<String, @JvmSuppressWildcards ExchangeMonitoringService>
) : ViewModel() {


    private val triples = listOf(
        Triple(ETH, BTC, USDT),
        Triple(ADA, BTC, USDT)
    )

    private val pairs = listOf(
        CurrencyPair(BTC, USDT),
        CurrencyPair(ETH, BTC)
    )

    private val regular = MutableStateFlow(ArbitrageModelRegular())
    fun regular(): StateFlow<ArbitrageModelRegular> = regular

    private val triangular = MutableStateFlow(ArbitrageModelTriangular())
    fun triangular(): StateFlow<ArbitrageModelTriangular> = triangular

    init {
        exchanges.forEach { (_, service) ->
            triples.forEach {
                scanTriangularOptions(service, it)
            }

            scanOptions(service, pairs)
        }
    }

    private fun scanOptions(
        service: ExchangeMonitoringService,
        pair: List<CurrencyPair>
    ) {
        viewModelScope.launch {
            service.regular(pair).collect {
                val model = regular.value.calc(it)
                regular.emit(model)
                delay(10)
            }
        }
    }

    private fun scanTriangularOptions(
        service: ExchangeMonitoringService,
        triple: Triple<Currency, Currency, Currency>
    ) {
        viewModelScope.launch {
            service.triangular(triple).collect {
                val arbitrageTrade = TriangularTrade(
                    id = UUID.randomUUID().toString(),
                    market = it.market,
                    arbitrage = it.arbitrages(),
                    ratios = it.ratios(),
                    fees = Triple(.08F, .08F, .08F)
                ) { clicked ->
                    val old = triangular.value.options - clicked
                    triangular.value = ArbitrageModelTriangular(options = old)
                }

                val old = triangular.value.options
                triangular.emit(ArbitrageModelTriangular(options = listOf(arbitrageTrade) + old))
                delay(10)
            }
        }
    }
}