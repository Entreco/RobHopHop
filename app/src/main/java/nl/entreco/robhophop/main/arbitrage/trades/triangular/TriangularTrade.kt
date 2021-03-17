package nl.entreco.robhophop.main.arbitrage.trades.triangular

import nl.entreco.robhophop.main.dashboard.Action

data class TriangularTrade(
    val id: String,
    val market: String,
    private val arbitrage: Triple<String, String, String>,
    private val ratios: Triple<Float, Float, Float>,
    private val fees: Triple<Float, Float, Float>,
    val clicker: (TriangularTrade) -> Unit
) {
    val first: String = arbitrage.first
    val second: String = arbitrage.second
    val third: String = arbitrage.third

    val firstToSecond: String = ratios.first.toString()
    val secondToThird: String = ratios.second.toString()
    val firstToThird: String = ratios.third.toString()

    private val grossProfit = ((1 * ratios.first / ratios.second * ratios.third) - 1) * 100
    val nettProfit: Float = grossProfit - fees.first - fees.second - fees.third
    private val sign = if (nettProfit >= 0) "+" else ""
    private val profit: String = String.format("%.4f", nettProfit)

    val displayProfit = "$sign$profit %"

    val action: Action = when {
        nettProfit < 0 -> Action.Buy
        nettProfit > 0 -> Action.Sell
        else -> Action.None
    }

    fun onClick() {
        clicker(this)
    }
}