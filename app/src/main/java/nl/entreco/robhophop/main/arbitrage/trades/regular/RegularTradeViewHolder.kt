package nl.entreco.robhophop.main.arbitrage.trades.regular

import androidx.recyclerview.widget.RecyclerView
import nl.entreco.robhophop.databinding.ViewholderRegularTradesBinding

class RegularTradeViewHolder(
    private val binding: ViewholderRegularTradesBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RegularTrade) {
        binding.item = item
        binding.executePendingBindings()
    }
}