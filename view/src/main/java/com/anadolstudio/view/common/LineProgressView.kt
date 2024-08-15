package com.anadolstudio.view.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.withStyledAttributes
import com.anadolstudio.utils.util.common.dpToPx
import com.anadolstudio.view.R

class LineProgressView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private companion object {
        val STROKE_WIDTH = 2F.dpToPx()
        const val MIN_TOTAL_PROGRESS = 1
    }


    var currentProgress: Int = 0
        set(value) {
            field = minOf(value, totalProgress)
            invalidate()
        }

    var totalProgress: Int = MIN_TOTAL_PROGRESS
        set(value) {
            field = maxOf(value, MIN_TOTAL_PROGRESS)
            invalidate()
        }


    private val defaultPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val progressPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        minimumHeight = STROKE_WIDTH.toInt()
        background = ColorDrawable(Color.TRANSPARENT)

        context.withStyledAttributes(attrs, R.styleable.LineProgressView, defStyleAttr, defStyleRes) {
            totalProgress = getInt(R.styleable.LineProgressView_totalProgress, MIN_TOTAL_PROGRESS)
            currentProgress = getInt(R.styleable.LineProgressView_currentProgress, 0)
            progressPaint.setupPaint(getInt(R.styleable.LineProgressView_colorForeground, Color.WHITE))
            defaultPaint.setupPaint(getInt(R.styleable.LineProgressView_colorBackground, Color.GRAY))
        }
    }

    private fun Paint.setupPaint(color: Int) {
        style = Paint.Style.STROKE
        strokeWidth = STROKE_WIDTH
        strokeCap = Paint.Cap.ROUND
        this.color = color
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var previousEndX = 0F

        for (i in 0 until totalProgress) {
            val endX = previousEndX + width / totalProgress

            val paint = if (i < currentProgress) progressPaint else defaultPaint
            canvas.drawRoundLine(previousEndX, endX, height / 2F, paint)

            previousEndX = endX
        }
    }

    private fun Canvas.drawRoundLine(startX: Float, endX: Float, y: Float, paint: Paint) {
        val correctStartX = startX + STROKE_WIDTH
        val correctEndX = endX - STROKE_WIDTH

        drawLine(correctStartX, y, correctEndX, y, paint)
    }

}
