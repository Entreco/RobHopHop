package nl.entreco.robhophop.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import nl.entreco.robhophop.RobKtx.viewModelProvider
import nl.entreco.robhophop.databinding.FragmentProfileBinding
import nl.entreco.robhophop.main.profile.di.component

class ProfileFragment : Fragment() {

    private val component by component()
    private val viewModel: ProfileViewModel by viewModelProvider { component.viewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
}