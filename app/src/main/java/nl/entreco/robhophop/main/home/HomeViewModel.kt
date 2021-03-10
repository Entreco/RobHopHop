package nl.entreco.robhophop.main.home

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import nl.entreco.exchange_core.Exchange
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    exchanges: Map<String, @JvmSuppressWildcards Exchange>
) : ViewModel(), AdapterView.OnItemSelectedListener {

    private val state = MutableStateFlow(HomeModel(exchanges = exchanges.values.toSet()))
    fun state(): StateFlow<HomeModel> = state

    private val events = MutableSharedFlow<HomeEvent>()
    fun events(): SharedFlow<HomeEvent> = events

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModelScope.launch {
            try {
                state.value = state.value.select(position)
                events.emit(HomeEvent.ExchangeSelected(state.value.selected))
            } catch (iob: IndexOutOfBoundsException) {
                state.value = state.value.none()
                events.emit(HomeEvent.ExchangeDeselected)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        viewModelScope.launch {
            state.value.none()
            events.emit(HomeEvent.ExchangeDeselected)
        }
    }

    fun onStartClicked() {
        viewModelScope.launch {
            val exchange = state.value.selected
            events.emit(HomeEvent.StartMonitoring(exchange))
        }
    }
}