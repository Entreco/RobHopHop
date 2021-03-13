package nl.entreco.robhophop.monitor

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.ExchangeMonitoringService
import java.math.BigDecimal
import javax.inject.Inject

class MonitorViewModel @Inject constructor(
    exchange: Exchange,
    private val service: ExchangeMonitoringService,
) : ViewModel() {

    val title = ObservableField(exchange.name)
    val market = ObservableField(exchange.market)

    private val state = MutableStateFlow(MonitorModel(valueDescription = "--", BigDecimal.ZERO, exchange.currency))
    fun state(): StateFlow<MonitorModel> = state

    private val events = MutableSharedFlow<MonitorEvent>()
    fun events(): SharedFlow<MonitorEvent> = events

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentValue = service.start()
            state.value = state.value.init(currentValue)

            service.monitor().onEach {
                state.value = state.value.update(it.second)
            }.collect()
        }
    }

    override fun onCleared() {
        service.stop()
        super.onCleared()
    }

}