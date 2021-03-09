package nl.entreco.robhophop.setup.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import nl.entreco.robhophop.R
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.setup.home.di.component

class HomeFragment : Fragment() {

    private val component by component()
    private val homeViewModel by viewModelProvider { component.viewModel() }
    private val homeAdapter by lazy { component.adapter() }
    private val navigator by lazy { component.navigator() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val spinner = setupSpinner(root)
        val button = setupButton(root)

        lifecycleScope.launchWhenResumed {

            homeViewModel.events().asLiveData(coroutineContext).observe(viewLifecycleOwner, navigator)
            homeViewModel.events().asLiveData(coroutineContext).observe(viewLifecycleOwner) {
                button.isEnabled = it is HomeEvent.ExchangeSelected
            }

            homeViewModel.state().collect {
                homeAdapter.submit(it.exchanges())
            }
        }
        return root
    }

    private fun setupSpinner(root: View): Spinner {
        val spinner: Spinner = root.findViewById(R.id.home_spinner)
        spinner.adapter = homeAdapter
        spinner.onItemSelectedListener = homeViewModel
        return spinner
    }

    private fun setupButton(root: View): Button {
        val button: Button = root.findViewById(R.id.home_start)
        button.setOnClickListener {
            homeViewModel.onStartClicked()
        }
        return button
    }
}