package nl.entreco.robhophop.main.arbitrage

import android.util.Log
import nl.entreco.exchange_core.Monitor
import nl.entreco.robhophop.main.arbitrage.trades.regular.RegularTrade
import nl.entreco.robhophop.main.dashboard.Action
import java.math.BigDecimal
import java.util.*
import kotlin.math.absoluteValue

data class ArbitrageModelRegular(
    val options: List<Monitor> = emptyList(),
    private val trades: MutableList<RegularTrade> = mutableListOf()
) {
    fun trades(): List<RegularTrade> = trades

    private val profitThreshold = 0.001f

    fun calc(monitor: Monitor): ArbitrageModelRegular {
        val tradingOptions = options.filter { it.pair == monitor.pair && it.exchange != monitor.exchange }
                .zipWithNext { one, two ->

                    val ratio: Float = try {
                        one.price.div(two.price).minus(BigDecimal.ONE).toFloat()
                    } catch (oops: ArithmeticException) {
                        0f
                    }

                    if (ratio > profitThreshold) {
                        RegularTrade(UUID.randomUUID().toString(), one, two, one.pair.toString().replace("-", "\n"), ratio, Action.Sell)
                    } else if (ratio < -profitThreshold) {
                        RegularTrade(UUID.randomUUID().toString(), two, one, two.pair.toString().replace("-", "\n"), ratio.absoluteValue, Action.Sell)
                    } else {
                        RegularTrade(UUID.randomUUID().toString(), one, two, one.pair.toString().replace("-", "\n"), -ratio, Action.Buy)
                    }
                }

        return copy(
            options = (options + monitor).takeLast(250).distinctBy { it.exchange.hashCode() + it.pair.hashCode() },
            trades = (trades + tradingOptions).takeLast(250).sortedByDescending { it.profit }.toMutableList()
        )
    }
}