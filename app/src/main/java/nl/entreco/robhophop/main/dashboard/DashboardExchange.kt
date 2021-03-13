package nl.entreco.robhophop.main.dashboard

import java.math.BigDecimal
import java.util.*

data class DashboardExchange(
    val name: String,
    val price: BigDecimal,
    val currency: String
)