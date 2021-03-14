package nl.entreco.charts

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import java.math.RoundingMode
import java.util.concurrent.atomic.AtomicBoolean

class ChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), LifecycleOwner {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val registry: LifecycleRegistry by lazy { LifecycleRegistry(this) }
    override fun getLifecycle() = registry

    private val mIsInit = AtomicBoolean(false)
    private val mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPath: Path = Path()
    private var mWidth = 0
    private var mHeight = 0
    private var mXUnit : Long = 0

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = 8f
        mPaint.color = Color.parseColor("#FF6200EE")
        mWidth = w
        mHeight = h
        mXUnit = System.currentTimeMillis()
        mPath.moveTo(0f, mHeight/2.toFloat())
        mIsInit.set(true)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    fun render(chart: Chart) {
        scope.launch {
            chart.stream().onEach {

                // gin to graph's origin
                val targetX : Float = (System.currentTimeMillis() - mXUnit) / 100f
                val targetY : Float = mHeight - (it.toInt() / 1000f)*mHeight

                mPath.lineTo(targetX, targetY)

                postInvalidate()

            }.collect()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (mIsInit.get()) {
            canvas?.drawPath(mPath, mPaint)
        }
    }

    override fun onDetachedFromWindow() {
        scope.cancel()
        super.onDetachedFromWindow()
    }
}

object ChartBinding {
    @JvmStatic
    @BindingAdapter("render")
    fun renderChart(view: ChartView, chart: Chart?) {
        chart?.let { view.render(it) }
    }
}