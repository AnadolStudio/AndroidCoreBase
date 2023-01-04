package com.anadolstudio.core.view.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import android.view.View.VISIBLE

object AnimateUtil {
    const val DURATION_SHORT: Long = 200
    const val DURATION_NORMAL: Long = 300
    const val DURATION_LONG: Long = 400
    const val DURATION_EXTRA_LONG: Long = 800

    const val REDUCE_SCALE_CLICK = 0.9F
    const val INCREASE_SCALE_CLICK = 1.1F

    private const val SCALE_DEFAULT = 1.0F

    fun showAnimX(view: View, start: Float, end: Float) {
        view.visibility = View.INVISIBLE
        view.translationX = start
        view.animate()
                .translationX(end)
                .setDuration(DURATION_NORMAL)
                .setListener(getSimpleStartListener(view))
    }

    fun showAnimX(view: View, start: Float, end: Float, visible: Int) {
        view.translationX = start
        view.animate()
                .translationX(end)
                .setDuration(DURATION_NORMAL)
                .setListener(getSimpleListener(view, visible))
    }

    fun showAnimX(view: View, start: Float, end: Float, listener: AnimatorListenerAdapter) {
        view.visibility = View.INVISIBLE
        view.translationX = start
        view.animate()
                .translationX(end)
                .setDuration(DURATION_NORMAL)
                .setListener(listener)
    }

    fun <T : View> fadOutAnimation(
            view: T,
            duration: Long = DURATION_SHORT,
            visibility: Int = View.INVISIBLE,
            completion: ((T) -> Unit)? = null
    ) {
        with(view) {
            animate()
                    .alpha(0f)
                    .setDuration(duration)
                    .withEndAction {
                        this.visibility = visibility
                        completion?.let { it(this) }
                    }
        }
    }

    fun <T : View> fadInAnimation(
            view: T,
            duration: Long = DURATION_SHORT, completion: ((T) -> Unit)? = null
    ) {
        with(view) {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                    .alpha(1f)
                    .setDuration(duration)
                    .withEndAction { completion?.let { it(this) } }
        }
    }

    //TODO UP, DOWN, LEFT, RIGHT
    fun showAnimY(view: View, start: Float, end: Float) {
        view.visibility = View.INVISIBLE
        view.translationY = start
        view.animate()
                .translationY(end)
                .setDuration(DURATION_NORMAL)
                .setListener(getSimpleStartListener(view))
    }

    fun showAnimY(view: View, start: Float, end: Float, listener: AnimatorListenerAdapter) {
        view.visibility = View.INVISIBLE
        view.translationY = start
        view.animate()
                .translationY(end)
                .setDuration(DURATION_NORMAL)
                .setListener(listener)
    }

    fun showAnimY(view: View, start: Float, end: Float, visible: Int) {
        view.translationY = start
        view.animate()
                .translationY(end)
                .setDuration(DURATION_NORMAL)
                .setListener(getSimpleListener(view, visible))
    }

    fun getSimpleStartListener(view: View) = getSimpleListener(view, VISIBLE)

    fun getSimpleEndListener(view: View) = getSimpleListener(view, View.GONE)

    fun getSimpleListener(view: View, visible: Int) = object : AnimatorListenerAdapter() {

        override fun onAnimationStart(animation: Animator) {
            super.onAnimationStart(animation)
            if (visible == VISIBLE) {
                view.clearAnimation()
                view.visibility = visible
            }
        }

        override fun onAnimationEnd(animation: Animator?) {
            super.onAnimationEnd(animation)
            if (visible != VISIBLE) {
                view.clearAnimation()
                view.visibility = visible
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun View.scaleAnimationOnClick(scale: Float = REDUCE_SCALE_CLICK, action: () -> Unit) = setOnTouchListener { _, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> scaleAnimation(scale)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> scaleAnimation(SCALE_DEFAULT, getActionOrNull(event, action))
        }

        return@setOnTouchListener true
    }

    private fun View.getActionOrNull(event: MotionEvent, action: (() -> Unit)): (() -> Unit)? {
        if (measuredWidth == 0 || measuredHeight == 0) return action

        val inBounds = event.x.toInt() in 0..measuredWidth
                && event.y.toInt() in 0..measuredHeight

        return if (inBounds) action else null
    }

    private fun View.scaleAnimation(scale: Float, action: (() -> Unit)? = null) = animate()
            .scaleX(scale)
            .scaleY(scale)
            .setDuration(DURATION_SHORT)
            .withEndAction { action?.invoke() }
            .start()
}
