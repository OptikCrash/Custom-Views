package com.digitalrelay.customviews.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.digitalrelay.customviews.R
import kotlin.math.roundToInt

class TimerView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val backgroundPaint = Paint()
    private val textPaint = TextPaint()

    private var startTime: Long = 0L
    private val timedUpdate = Runnable {
        updateTimer()
    }

    init {
        backgroundPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        textPaint.color = ContextCompat.getColor(context, android.R.color.white)
        // When setting text always do sp * dp
        textPaint.textSize = 32f * resources.displayMetrics.scaledDensity
    }

    private fun updateTimer() {
        invalidate()
        postDelayed(timedUpdate, 200L)
    }

    override fun onDraw(canvas: Canvas) {
        val centerX = canvas.width * 0.5f
        val centerY = canvas.height * 0.5f
        val radius = (if (canvas.width < canvas.height) canvas.width else canvas.height) * 0.5f
        val count = ((System.currentTimeMillis() - startTime) * 0.001).roundToInt().toString()
        val textOffsetX = textPaint.measureText(count) * 0.5f
        val textOffsetY = textPaint.fontMetrics.ascent * -0.4f
        canvas.drawCircle(centerX, centerY, radius, backgroundPaint)
        canvas.drawText(count, centerX - textOffsetX, centerY + textOffsetY, textPaint)
    }

    fun start() {
        startTime = System.currentTimeMillis()
        updateTimer()
    }
    fun stop() {
        startTime = 0L
        removeCallbacks(timedUpdate)
    }
}