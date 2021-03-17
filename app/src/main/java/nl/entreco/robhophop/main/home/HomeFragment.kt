package nl.entreco.robhophop.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.databinding.FragmentHomeBinding
import nl.entreco.robhophop.main.home.di.component

class HomeFragment : Fragment() {

    private val component by component()
    private val viewModel by viewModelProvider { component.viewModel() }
    private val navigator by lazy { component.navigator() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
}