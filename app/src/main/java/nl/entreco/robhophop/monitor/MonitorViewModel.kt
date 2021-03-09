package nl.entreco.robhophop.monitor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import nl.entreco.exchange_core.ExchangeMonitoringService
import java.math.BigDecimal
import javax.inject.Inject

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class MonitorViewModel @Inject constructor(
    private val monitoringService: ExchangeMonitoringService
) : ViewModel() {

    private val state = MutableStateFlow(MonitorModel(valueDescription = "--", BigDecimal.ZERO))

    fun state(): StateFlow<MonitorModel> = state

    private val events = MutableSharedFlow<MonitorEvent>()
    fun events(): SharedFlow<MonitorEvent> = events

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val currentValue = monitoringService.start()
            state.value = state.value.init(currentValue)

            monitoringService.monitor().onEach {
                Log.d("WOAH", "collecting: $it")
                state.value = state.value.update(it)
            }.collect()
        }
    }

    override fun onCleared() {
        monitoringService.stop()
        super.onCleared()
    }

}