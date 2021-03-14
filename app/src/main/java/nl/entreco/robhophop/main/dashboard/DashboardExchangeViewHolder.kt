package nl.entreco.robhophop.main.dashboard

import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.entreco.robhophop.R
import nl.entreco.robhophop.databinding.ViewholderDashboardExchangeBinding

data class DashboardExchangeViewHolder(
    private val binding: ViewholderDashboardExchangeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DashboardExchange) {
        binding.item = item
        binding.executePendingBindings()
    }
}

object DashboardExchangeBindingP {

    @JvmStatic
    @BindingAdapter("actionIcon")
    fun actionIcon(view: TextView, action: Action) {
        val icon = when (action) {
            Action.Buy -> R.drawable.ic_buy
            Action.Sell -> R.drawable.ic_sell
            else -> 0
        }
        if (icon != 0) view.setCompoundDrawablesRelativeWithIntrinsicBounds(0, icon, 0, 0)
    }
}