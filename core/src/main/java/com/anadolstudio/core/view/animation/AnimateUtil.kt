package com.anadolstudio.core.view.animation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

object AnimateUtil { // TODO Нужен родитель с базовыми методами

    const val DURATION_SHORT: Long = 200
    const val DURATION_NORMAL: Long = 300
    const val DURATION_LONG: Long = 400
    const val DURATION_EXTRA_LONG: Long = 800

    @JvmStatic
    fun View.hideTranslationHorizontal(direction: Horizontal) {
        simpleTranslationX(this, direction, getSimpleEndListener(this))
    }

    @JvmStatic
    fun View.showTranslationHorizontal(direction: Horizontal) {
        simpleTranslationX(this, direction, getSimpleStartListener(this))
    }

    @JvmStatic
    fun View.hideTranslationVertical(direction: Vertical) {
        simpleTranslationY(this, direction, getSimpleEndListener(this))
    }

    @JvmStatic
    fun View.showTranslationVertical(direction: Vertical) {
        simpleTranslationY(this, direction, getSimpleStartListener(this))
    }

    fun simpleTranslationX(
            view: View,
            direction: Horizontal,
            listener: AnimatorListenerAdapter = getSimpleStartListener(view)
    ) {
        val start = when (direction) {
            Horizontal.TO_END -> 0F
            Horizontal.TO_START -> view.width.toFloat()
        }

        val end = when (direction) {
            Horizontal.TO_END -> view.width.toFloat()
            Horizontal.TO_START -> 0F
        }

        translationX(view, start, end, listener)
    }

    fun translationX(
            view: View,
            start: Float,
            end: Float,
            listener: AnimatorListenerAdapter = getSimpleStartListener(view)
    ) {
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

    fun simpleTranslationY(
            view: View,
            direction: Vertical,
            listener: AnimatorListenerAdapter = getSimpleStartListener(view)
    ) {
        val start = when (direction) {
            Vertical.TO_BOTTOM -> 0F
            Vertical.TO_TOP -> view.height.toFloat()
        }

        val end = when (direction) {
            Vertical.TO_BOTTOM -> view.height.toFloat()
            Vertical.TO_TOP -> 0F
        }

        translationY(view, start, end, listener)
    }

    @JvmStatic
    fun translationY(
            view: View,
            start: Float,
            end: Float,
            listener: AnimatorListenerAdapter = getSimpleStartListener(view)
    ) {
        view.translationY = start
        view.animate()
                .translationY(end)
                .setDuration(DURATION_NORMAL)
                .setListener(listener)
    }

    //TODO один AnimatorListenerAdapter с прокидыванием для случателей
    fun getSimpleStartListener(view: View, visible: Int = View.VISIBLE) = object : AnimatorListenerAdapter() {
        override fun onAnimationStart(animation: Animator) {
            super.onAnimationStart(animation)
            view.clearAnimation()
            view.visibility = visible
        }
    }

    fun getSimpleEndListener(view: View) = getSimpleEndListener(view, View.INVISIBLE)

    fun getSimpleEndListener(view: View, visible: Int) = object : AnimatorListenerAdapter() {
        override fun onAnimationEnd(animation: Animator) {
            super.onAnimationStart(animation)
            view.clearAnimation()
            view.visibility = visible
        }
    }

    enum class Vertical {
        TO_BOTTOM,
        TO_TOP
    }

    enum class Horizontal {
        TO_START,
        TO_END
    }

}
