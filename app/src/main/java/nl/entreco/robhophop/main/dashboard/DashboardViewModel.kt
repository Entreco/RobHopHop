package nl.entreco.robhophop.main.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService
import java.math.BigDecimal
import java.math.RoundingMode
import javax.inject.Inject

class DashboardViewModel @Inject constructor(
    private val exchanges: Map<String, @JvmSuppressWildcards Exchange>,
    private val monitors: Map<String, @JvmSuppressWildcards ExchangeMonitoringService>
) : ViewModel() {

    private val meanAverage = MutableStateFlow<BigDecimal>(BigDecimal.ZERO)
    fun average(): StateFlow<BigDecimal> = meanAverage

    private val state = MutableStateFlow(DashboardModel())
    fun state(): StateFlow<DashboardModel> = state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val markets = initialMarkets(exchanges)
            val list = markets.values.toList()
            state.emit(DashboardModel(list))

            val last5 = mutableListOf<BigDecimal>()
            val exchangeMonitors = monitors.values.map { it.monitor() }.toTypedArray()
            flowOf(*exchangeMonitors).flattenMerge().collect { pair ->
                state.emit(updateExchange(markets, pair.first.name, pair.second))

                if (pair.first.market == "BTC-USDT") {
                    last5.add(0, pair.second)
                    if (last5.size > 5) last5.removeLast()
                    val average = last5.map { deci -> deci.toFloat() }.average()
                    meanAverage.emit(BigDecimal(average))
                }
            }
        }
    }

    private fun updateExchange(
        markets: MutableMap<String, DashboardExchange>,
        exchange: String,
        price: BigDecimal
    ): DashboardModel {

        val diff = 1 - (price.toFloat() / meanAverage.value.toFloat())
        val type = when {
            diff < -0.0025 -> Action.Buy
            diff > 0.0025 -> Action.Sell
            else -> Action.None
        }
        markets[exchange] =
            markets[exchange]!!.copy(
                price = price.setScale(2, RoundingMode.HALF_EVEN),
                action = type
            )
        return state.value.copy(markets.values.toList())
    }

    private fun initialMarkets(exchanges: Map<String, @JvmSuppressWildcards Exchange>) =
        exchanges.map {
            it.key to DashboardExchange(
                it.value.name,
                BigDecimal.ZERO,
                it.value.currency,
                Action.None
            )
        }.toMap().toMutableMap()

    override fun onCleared() {
        monitors.values.forEach { it.stop() }
        super.onCleared()
    }
}