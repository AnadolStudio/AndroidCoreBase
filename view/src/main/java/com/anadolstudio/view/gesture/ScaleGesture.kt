package com.anadolstudio.view.gesture

import android.view.ScaleGestureDetector
import com.anadolstudio.utils.util.extentions.onTrue
import kotlin.math.abs

class ScaleGesture(
        private val onIncrease: () -> Unit,
        private val onDecrease: () -> Unit,
) : ScaleGestureDetector.SimpleOnScaleGestureListener() {

    private companion object {
        const val DELAY = 750L
        const val SPAN_SLOP = 150
    }

    private var previousHandleTime: Long = 0

    override fun onScale(detector: ScaleGestureDetector): Boolean {

        return when {
            detector.scaleFactor < 1F && detector.gestureTolerance() -> true.also { onIncrease.invoke() }
            detector.scaleFactor > 1F && detector.gestureTolerance() -> true.also { onDecrease.invoke() }
            else -> false
        }.onTrue {
            previousHandleTime = System.currentTimeMillis()
        }
    }

    private fun ScaleGestureDetector.gestureTolerance(): Boolean {
        val spanDelta = abs(currentSpan - previousSpan)

        return spanDelta > SPAN_SLOP && isNotPause()
    }

    private fun isNotPause(): Boolean = System.currentTimeMillis() - previousHandleTime > DELAY

}
