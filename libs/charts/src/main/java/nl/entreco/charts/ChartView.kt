package nl.entreco.charts

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter

class ChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

}

object ChartBinding {
    @BindingAdapter("render")
    fun renderChart(view: ChartView, chart: Chart) {

    }
}