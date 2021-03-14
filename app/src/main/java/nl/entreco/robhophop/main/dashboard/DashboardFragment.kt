package nl.entreco.robhophop.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.entreco.robhophop.R
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.databinding.FragmentDashboardBinding
import nl.entreco.robhophop.main.dashboard.di.component
import java.math.RoundingMode

class DashboardFragment : Fragment() {

    private val component by component()
    private val viewModel by viewModelProvider { component.viewModel() }
    private val adapter by lazy { component.adapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecycler(binding.recycler)
        setupTitle(binding.includeAppbar.collapsingToolbar)
        return binding.root
    }

    private fun setupTitle(toolbar: CollapsingToolbarLayout) {
        lifecycleScope.launch {
            viewModel.average().collect {
                toolbar.title = getString(R.string.current_average, it.setScale(2, RoundingMode.HALF_EVEN).toEngineeringString())
            }
        }
    }

    private fun setupRecycler(recycler: RecyclerView) {
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(requireContext(), 1)
        recycler.itemAnimator = null
        recycler.setHasFixedSize(true)

        lifecycleScope.launch {
            viewModel.state().collect {
                adapter.submitList(it.exchangePrizes)
            }
        }
    }
}