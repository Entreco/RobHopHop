package nl.entreco.robhophop.main.trades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import nl.entreco.exchange_core.ExchangeMonitoringService
import nl.entreco.robhophop.main.dashboard.Action
import java.math.BigDecimal
import javax.inject.Inject

class TradesViewModel @Inject constructor(
    private val exchanges: Map<String, @JvmSuppressWildcards ExchangeMonitoringService>
) : ViewModel() {

    private val profitMargin = 0.001
    private val exchangeFee = 0.0025

    private val state = MutableStateFlow(TradesModel(exchanges.keys.first(), exchanges.keys.last()))
    fun state(): StateFlow<TradesModel> = state

    init {
        require(exchanges.size == 2) { "Need 2 Modules! -> check TradesComponent: @Component(modules = []" }
        viewModelScope.launch(Dispatchers.IO) {

            val exchange1 = exchanges.keys.first()
            val tradingFlow1 = exchanges.values.first().monitor()

            val exchange2 = exchanges.keys.last()
            val tradingFlow2 = exchanges.values.last().monitor()

            tradingFlow1.zip(tradingFlow2) { binance, huobi ->

                val expectedProfit = BigDecimal.ONE - (binance.second / huobi.second) - BigDecimal(2) * BigDecimal(exchangeFee)
                val updatedProfit = state.value.expectedProfit + expectedProfit.abs()

                when {
                    expectedProfit > BigDecimal(profitMargin) -> {
                        delay(500)
                        TradesModel(exchange1, exchange2, binance.second, huobi.second, Action.Sell, Action.Buy, updatedProfit)
                    }
                    expectedProfit < BigDecimal(-profitMargin) -> {
                        delay(500)
                        TradesModel(exchange1, exchange2, binance.second, huobi.second, Action.Buy, Action.Sell, updatedProfit)
                    }
                    else -> {
                        TradesModel(exchange1, exchange2, binance.second, huobi.second, Action.None, Action.None, state.value.expectedProfit)
                    }
                }

            }.collect {
                state.emit( it )
            }
        }
    }

    override fun onCleared() {
        exchanges.forEach { it.value.stop() }
        super.onCleared()
    }
}