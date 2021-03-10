package nl.entreco.robhophop.main.home

import nl.entreco.exchange_core.Exchange
import nl.entreco.exchange_core.NoExchange
import nl.entreco.robhophop.main.home.exchanges.HomeExchangeItem

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
sealed class HomeEvent {
    object ExchangeDeselected : HomeEvent()
    data class ExchangeSelected(val exchange: Exchange) : HomeEvent()
    data class StartMonitoring(val exchange: Exchange) : HomeEvent()
}

data class HomeModel(
    val selected: Exchange = NoExchange(),
    private val exchanges: Set<Exchange>,
) {
    fun exchanges(): List<HomeExchangeItem> =
        exchanges.mapIndexed { index, exchange -> HomeExchangeItem(index, exchange.name) }

    fun select(position: Int): HomeModel = copy(selected = exchanges.elementAt(position))
    fun none(): HomeModel = copy(selected = NoExchange())
}