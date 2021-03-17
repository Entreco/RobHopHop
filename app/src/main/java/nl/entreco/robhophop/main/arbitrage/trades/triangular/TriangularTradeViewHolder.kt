package nl.entreco.robhophop.main.arbitrage.trades.triangular

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import nl.entreco.robhophop.R
import nl.entreco.robhophop.databinding.ViewholderTriangularTradesBinding
import nl.entreco.robhophop.main.dashboard.Action

class ArbitrageTradeViewHolder(
    private val binding: ViewholderTriangularTradesBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: TriangularTrade) {
        binding.item = item
        binding.executePendingBindings()
    }
}

object ArbitrageExchangeBinding {

    @JvmStatic
    @BindingAdapter("tradeEnabled")
    fun tradeEnabled(view: MaterialButton, action: Action) {
        view.isEnabled = action == Action.Sell
    }

    @JvmStatic
    @BindingAdapter("profitColor")
    fun profitColor(view: TextView, action: Action) {
        val id = when (action) {
            Action.Buy -> R.color.color_sell
            else -> R.color.color_buy
        }
        view.setTextColor(view.resources.getColorStateList(id, view.context.theme))
    }
}