package nl.entreco.robhophop.main.home

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
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
            is HomeEvent.StartMonitoring -> context.startActivity(Intent("nl.entreco.robhophop.Monitor"))
        }
    }
}