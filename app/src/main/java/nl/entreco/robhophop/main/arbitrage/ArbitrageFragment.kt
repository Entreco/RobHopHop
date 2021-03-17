package nl.entreco.robhophop.main.arbitrage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.databinding.FragmentArbitrageBinding
import nl.entreco.robhophop.main.arbitrage.di.component

class ArbitrageFragment : Fragment() {

    private val component by component()
    private val viewModel by viewModelProvider { component.viewModel() }
    private val regularAdapter by lazy { component.regularAdapter() }
    private val triangleAdapter by lazy { component.triangleAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentArbitrageBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerRegular(binding.regular)
        setupRecyclerTriangular(binding.triangular)
        return binding.root
    }

    private fun setupRecyclerRegular(recycler: RecyclerView) {
        recycler.adapter = regularAdapter
        recycler.layoutManager = GridLayoutManager(requireContext(), 1)
        recycler.itemAnimator = null
        recycler.setHasFixedSize(true)

        lifecycleScope.launch {
            viewModel.regular().collect {
                regularAdapter.submitList(it.trades())
            }
        }
    }

    private fun setupRecyclerTriangular(recycler: RecyclerView) {
        recycler.adapter = triangleAdapter
        recycler.layoutManager = GridLayoutManager(requireContext(), 1)
        recycler.itemAnimator = null
        recycler.setHasFixedSize(true)

        lifecycleScope.launch {
            viewModel.triangular().collect {
                triangleAdapter.submitList(it.options
                    .distinctBy { it.market + it.first }
                    .sortedByDescending { it.nettProfit }
                )
            }
        }
    }
}