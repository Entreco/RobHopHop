package nl.entreco.robhophop.monitor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import nl.entreco.robhophop.R
import nl.entreco.robhophop.RobKtx.hideSystemUi
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.databinding.ActivityMonitorBinding
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
        hideSystemUi()
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityMonitorBinding>(this, R.layout.activity_monitor)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        lifecycleScope.launchWhenResumed {
            viewModel.state().collect {
                binding.monitorBtc.text = getString(R.string.current_price, it.valueDescription)
            }
        }
    }
}