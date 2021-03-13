package nl.entreco.robhophop.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import nl.entreco.robhophop.R
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.databinding.FragmentDashboardBinding
import nl.entreco.robhophop.main.dashboard.di.component

class DashboardFragment : Fragment() {

    private val component by component()
    private val viewModel by viewModelProvider { component.viewModel() }
    private val adapter by lazy { component.adapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentDashboardBinding>(inflater, R.layout.fragment_dashboard, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        lifecycleScope.launchWhenResumed {
            setupRecycler(binding.recycler)
        }
        return binding.root
    }

    private suspend fun setupRecycler(recycler: RecyclerView) {
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(requireContext(), 1)
        recycler.itemAnimator = null
        recycler.setHasFixedSize(true)
        viewModel.state().collect {
            adapter.submitList(it.exchangePrizes)
        }
    }
}