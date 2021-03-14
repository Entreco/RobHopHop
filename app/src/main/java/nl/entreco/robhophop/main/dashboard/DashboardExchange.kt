package nl.entreco.robhophop.main.dashboard

import java.math.BigDecimal

data class DashboardExchange(
    val name: String,
    val price: BigDecimal,
    val currency: String,
    val action: Action
)

sealed class Action {
    object Buy : Action()
    object Sell : Action()
    object None : Action()

    override fun toString() = when (this) {
        is Buy -> "BUY"
        is Sell -> "SELL"
        else -> ""
    }
}