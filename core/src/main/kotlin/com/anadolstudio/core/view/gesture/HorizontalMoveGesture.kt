package com.anadolstudio.core.view.gesture

import android.view.GestureDetector
import android.view.MotionEvent
import com.anadolstudio.core.util.common_extention.MathUtil
import com.anadolstudio.core.util.common_extention.MathUtil.toAngle
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.sin

class HorizontalMoveGesture(
        private val width: Int,
        private val boundSize: Int? = null,
        private val onSwipeRight: (() -> Unit)? = null,
        private val onSwipeLeft: (() -> Unit)? = null,
) : GestureDetector.SimpleOnGestureListener() {

    private companion object {
        const val SWIPE_THRESHOLD = 50
        const val SWIPE_VELOCITY_THRESHOLD = 100
        const val VALID_ANGLE_MOVE_DELTA = 25F
    }

    private fun onSwipeRight() {
        onSwipeRight?.invoke()
    }

    private fun onSwipeLeft() {
        onSwipeLeft?.invoke()
    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
        var result = false
        e1 ?: return false

        if (boundSize != null && e1.x.toInt() in boundSize until width - boundSize) return false

        val diffX = e2.x - e1.x
        val diffY = e2.y - e1.y

        val absX = abs(diffX)
        val absY = abs(diffY)
        val angle = asin(sin(absY / MathUtil.radius(absX, absY))).toAngle()

        val isValidAngleMove = angle < VALID_ANGLE_MOVE_DELTA

        if (isValidAngleMove && absX > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            if (diffX > 0) {
                onSwipeRight()
            } else {
                onSwipeLeft()
            }

            result = true
        }

        return result
    }
}
