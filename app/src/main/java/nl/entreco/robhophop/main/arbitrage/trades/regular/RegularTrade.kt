package nl.entreco.robhophop.main.arbitrage.trades.regular

import nl.entreco.exchange_core.Monitor
import nl.entreco.robhophop.main.dashboard.Action

data class RegularTrade(
    val id: String,
    val from: Monitor?,
    val to: Monitor?,
    val currency: String,
    val profit: Float,
    val action: Action
){
    fun expectedProfit(): String = String.format("%.4f", profit) + "%"
}