package nl.entreco.robhophop.pin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import nl.entreco.robhophop.R
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.databinding.ActivityPinBinding
import nl.entreco.robhophop.pin.di.component

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class PinActivity : AppCompatActivity() {

    private val component by component()
    private val viewModel by viewModelProvider { component.viewModel() }
    private val navigator by lazy { component.navigator() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityPinBinding>(this, R.layout.activity_pin)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        navigator.onChanged(PinEvent.Check)

        lifecycleScope.launchWhenCreated {
            viewModel.events().asLiveData(coroutineContext).observe(this@PinActivity, navigator)
        }
    }
}