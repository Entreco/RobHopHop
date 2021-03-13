package nl.entreco.robhophop.main.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import nl.entreco.robhophop.R
import nl.entreco.robhophop.databinding.ViewholderDashboardExchangeBinding
import javax.inject.Inject

class DashboardListAdapter @Inject constructor(
) : ListAdapter<DashboardExchange, DashboardExchangeViewHolder>(differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardExchangeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewholderDashboardExchangeBinding>(
            inflater,
            R.layout.viewholder_dashboard_exchange,
            parent,
            false
        )
        return DashboardExchangeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DashboardExchangeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

val differ = object : DiffUtil.ItemCallback<DashboardExchange>() {
    override fun areItemsTheSame(oldItem: DashboardExchange, newItem: DashboardExchange) =
        oldItem.name == newItem.name

    override fun areContentsTheSame(
        oldItem: DashboardExchange,
        newItem: DashboardExchange
    ) = oldItem == newItem
}