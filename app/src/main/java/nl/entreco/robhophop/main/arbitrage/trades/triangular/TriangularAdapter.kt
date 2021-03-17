package nl.entreco.robhophop.main.arbitrage.trades.triangular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import nl.entreco.robhophop.R
import nl.entreco.robhophop.databinding.ViewholderTriangularTradesBinding
import javax.inject.Inject

class TriangularAdapter @Inject constructor(
) : ListAdapter<TriangularTrade, ArbitrageTradeViewHolder>(differ){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArbitrageTradeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewholderTriangularTradesBinding>(
            inflater,
            R.layout.viewholder_triangular_trades,
            parent,
            false
        )
        return ArbitrageTradeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArbitrageTradeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


val differ = object : DiffUtil.ItemCallback<TriangularTrade>() {
    override fun areItemsTheSame(oldItem: TriangularTrade, newItem: TriangularTrade) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: TriangularTrade,
        newItem: TriangularTrade
    ) = oldItem == newItem
}