package nl.entreco.robhophop.main.dashboard

import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import nl.entreco.robhophop.R
import nl.entreco.robhophop.databinding.ViewholderDashboardExchangeBinding

class DashboardExchangeViewHolder(
    private val binding: ViewholderDashboardExchangeBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DashboardExchange) {
        binding.item = item
        binding.executePendingBindings()
    }
}

object DashboardExchangeBinding {

    @JvmStatic
    @BindingAdapter("actionIcon")
    fun actionIcon(view: TextView, action: Action) {
        val icon = when (action) {
            Action.Buy -> R.drawable.ic_sell
            Action.Sell -> R.drawable.ic_buy
            else -> R.drawable.ic_none
        }
        if (icon != 0) view.setCompoundDrawablesRelativeWithIntrinsicBounds(0, icon, 0, 0)
    }

    @JvmStatic
    @BindingAdapter("exchangeIcon")
    fun exchangeIcon(view: TextView, @DrawableRes logo: Int) {
        if (logo != 0) view.setCompoundDrawablesRelativeWithIntrinsicBounds(logo, 0, 0, 0)
    }
}