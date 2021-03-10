package nl.entreco.robhophop.main.home

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import nl.entreco.exchange_core.Exchange
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
class HomeNavigator @Inject constructor(
    private val context: Context
) : Observer<HomeEvent> {

    override fun onChanged(event: HomeEvent?) {
        when (event) {
            is HomeEvent.StartMonitoring -> launchMonitor(event.exchange)
        }
    }

    private fun launchMonitor(exchange: Exchange) {
        val intent = Intent("nl.entreco.robhophop.Monitor").apply {
            putExtra("extra_exchange", exchange.name)
        }
        context.startActivity(intent)
    }
}