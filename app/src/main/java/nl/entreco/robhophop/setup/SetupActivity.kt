package nl.entreco.robhophop.setup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import nl.entreco.robhophop.R
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.databinding.ActivitySetupBinding
import nl.entreco.robhophop.setup.di.component

/*************************************************************************
 *
 * ONWARD CONFIDENTIAL
 * __________________
 *
 *  [2021] ONWARD
 *  All Rights Reserved.
 *
 */
class SetupActivity : AppCompatActivity() {

    private val component by component()
    private val viewModel by viewModelProvider { component.viewModel() }
    private val navigator by lazy { component.navigator() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySetupBinding>(this, R.layout.activity_setup)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        lifecycleScope.launchWhenCreated {
            viewModel.events().asLiveData(coroutineContext).observe(this@SetupActivity, navigator)
        }

    }
}