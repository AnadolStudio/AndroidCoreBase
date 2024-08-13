package com.anadolstudio.utils.animation

import android.view.MotionEvent
import android.view.View
import android.view.animation.DecelerateInterpolator
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class ScaleOnTouchListener(
        private val onTouchScale: Float,
        private val defaultScale: Float,
        private val actionDown: (() -> Unit)? = null,
        private val actionCancel: (() -> Unit)? = null,
        private val onAnimationEnd: (() -> Unit)? = null,
        private val onLongPress: (() -> Unit)? = null,
        private val longPressDelayMillis: Long = TimeUnit.SECONDS.toMillis(1),
        private val actionUp: () -> Unit,
) {

    private var longDisposable: Disposable? = null
    private var longPressConsumed:Boolean = false

    fun onTouchListener(view: View, event: MotionEvent): Boolean {
        view.scaleOnTouch(event)

        return true
    }

    private fun View.scaleOnTouch(
            event: MotionEvent,
    ) = when (event.action) {
        MotionEvent.ACTION_DOWN -> {
            if (onLongPress != null) {
                resetLongPress()

                longDisposable = Completable.complete()
                        .delay(longPressDelayMillis, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            longPressConsumed = true
                            onLongPress.invoke()
                        }
            }

            actionDown?.invoke()
            scaleAnimation(onTouchScale)
        }

        MotionEvent.ACTION_CANCEL -> {
            actionCancel?.invoke()
            resetLongPress()

            scaleAnimation(scale = defaultScale, onAnimationEnd = onAnimationEnd)
        }

        MotionEvent.ACTION_UP -> {
            var action = getActionOrNull(event, actionUp)
            if (longPressConsumed) action = null

            resetLongPress()

            scaleAnimation(scale = defaultScale, action = action, onAnimationEnd)
        }
        else -> Unit
    }

    private fun resetLongPress() {
        longDisposable?.dispose()
        longPressConsumed = false
    }

    private fun View.getActionOrNull(event: MotionEvent, action: (() -> Unit)): (() -> Unit)? {
        if (measuredWidth == 0 || measuredHeight == 0) return action

        val inBounds = event.x.toInt() in 0..measuredWidth
                && event.y.toInt() in 0..measuredHeight

        return if (inBounds) action else null
    }

    private fun View.scaleAnimation(
            scale: Float,
            action: (() -> Unit)? = null,
            onAnimationEnd: (() -> Unit)? = null
    ) = animate()
            .scaleX(scale)
            .scaleY(scale)
            .setDuration(AnimateUtil.DURATION_EXTRA_SHORT)
            .withStartAction { action?.invoke() }
            .withEndAction { onAnimationEnd?.invoke() }
            .setInterpolator(DecelerateInterpolator())
            .start()

}
