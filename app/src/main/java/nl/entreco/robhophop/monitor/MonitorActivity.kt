package nl.entreco.robhophop.monitor

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.entreco.robhophop.R
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.monitor.di.component

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class MonitorActivity : AppCompatActivity() {

    private val component by component()
    private val viewModel by viewModelProvider { component.viewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor)
        val btc = setupBtc()

        lifecycleScope.launch {
            viewModel.state().collect {
                btc.text = "${it.valueDescription} EUR"
            }
        }
    }

    private fun setupBtc(): TextView {
        val btc = findViewById<TextView>(R.id.monitor_btc)
        return btc
    }
}