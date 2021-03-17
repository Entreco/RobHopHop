package nl.entreco.robhophop.main.dashboard

import nl.entreco.robhophop.R
import java.math.BigDecimal

data class DashboardExchange(
    val name: String,
    val price: BigDecimal,
    val currency: String,
    val action: Action,
    val logo: Int = R.drawable.ic_none
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