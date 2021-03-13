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

    private val state = MutableStateFlow(DashboardModel())
    fun state(): StateFlow<DashboardModel> = state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val markets = initialMarkets(exchanges)
            val list = markets.values.toList()
            state.emit(DashboardModel(list))

            val exchangeMonitors = monitors.values.map { it.monitor() }.toTypedArray()
            flowOf(*exchangeMonitors).flattenMerge().collect {
                state.emit(updateExchange(markets, it.first.name, it.second))
            }
        }
    }

    private fun updateExchange(
        markets: MutableMap<String, DashboardExchange>,
        exchange: String,
        price: BigDecimal
    ): DashboardModel {
        markets[exchange] = markets[exchange]!!.copy(price = price.setScale(2, RoundingMode.HALF_EVEN))
        return state.value.copy(markets.values.toList())
    }

    private fun initialMarkets(exchanges: Map<String, @JvmSuppressWildcards Exchange>) =
        exchanges.map {
            it.key to DashboardExchange(it.value.name, BigDecimal.ZERO, it.value.currency)
        }.toMap().toMutableMap()

    override fun onCleared() {
        monitors.values.forEach { it.stop() }
        super.onCleared()
    }
}