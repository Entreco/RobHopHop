package nl.entreco.robhophop.main.dashboard

import androidx.recyclerview.widget.RecyclerView
import nl.entreco.robhophop.databinding.ViewholderDashboardExchangeBinding

data class DashboardExchangeViewHolder(
    private val binding: ViewholderDashboardExchangeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DashboardExchange) {
        binding.item = item
        binding.executePendingBindings()
    }
}