package com.anadolstudio.core.view.gesture

import android.view.ScaleGestureDetector
import com.anadolstudio.core.util.common_extention.onTrue

class ScaleGesture(
        private val onIncrease: () -> Unit,
        private val onDecrease: () -> Unit,
) : ScaleGestureDetector.SimpleOnScaleGestureListener() {

    private companion object {
        const val DELAY = 750L
    }

    private var previousHandleTime: Long = 0

    override fun onScale(detector: ScaleGestureDetector): Boolean {

        return when {
            detector.scaleFactor < 1F && isNotPause() -> true.also { onIncrease.invoke() }
            detector.scaleFactor > 1F && isNotPause() -> true.also { onDecrease.invoke() }
            else -> false
        }.onTrue {
            previousHandleTime = System.currentTimeMillis()
        }
    }

    private fun isNotPause(): Boolean = System.currentTimeMillis() - previousHandleTime > DELAY
}
