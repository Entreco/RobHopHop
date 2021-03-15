package nl.entreco.robhophop.main.trades

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.entreco.robhophop.R
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.databinding.FragmentTradesBinding
import nl.entreco.robhophop.main.dashboard.DashboardExchangeBindingP
import nl.entreco.robhophop.main.trades.di.component

class TradesFragment : Fragment() {

    private val component by component()
    private val viewModel by viewModelProvider { component.viewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentTradesBinding>(
            inflater,
            R.layout.fragment_trades,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        lifecycleScope.launch {
            viewModel.state().collect{
                binding.exchange1.text = it.exchange1
                binding.exchange2.text = it.exchange2
                binding.value1.text = String.format("%.2f", it.trading1.toFloat())
                binding.value2.text = String.format("%.2f", it.trading2.toFloat())
                binding.action1.text = it.action1.toString()
                binding.action2.text = it.action2.toString()
                DashboardExchangeBindingP.actionIcon(binding.action1, it.action1)
                DashboardExchangeBindingP.actionIcon(binding.action2, it.action2)
                binding.expectedProfit.text = String.format("%.2f", it.expectedProfit.toFloat()) + "%"
            }
        }

        return binding.root
    }
}