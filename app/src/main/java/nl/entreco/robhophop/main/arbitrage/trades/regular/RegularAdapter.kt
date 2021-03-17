package nl.entreco.robhophop.main.arbitrage.trades.regular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import nl.entreco.robhophop.R
import nl.entreco.robhophop.databinding.ViewholderRegularTradesBinding
import javax.inject.Inject

class RegularAdapter @Inject constructor(

) : ListAdapter<RegularTrade, RegularTradeViewHolder>(differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegularTradeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewholderRegularTradesBinding>(
            inflater,
            R.layout.viewholder_regular_trades,
            parent,
            false
        )
        return RegularTradeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegularTradeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


val differ = object : DiffUtil.ItemCallback<RegularTrade>() {
    override fun areItemsTheSame(oldItem: RegularTrade, newItem: RegularTrade) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: RegularTrade,
        newItem: RegularTrade
    ) = oldItem == newItem
}