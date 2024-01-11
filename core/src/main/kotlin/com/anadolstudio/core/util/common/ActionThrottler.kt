package com.anadolstudio.core.util.common

import android.os.SystemClock
import android.view.View

open class ActionThrottler(private val defaultDelay: Long) {
    private var lastActionTime = 0L

    fun throttleAction(delay: Long = defaultDelay, onThrottled: () -> Unit = {}, action: () -> Unit) {
        throttleActionWithResult(delay, onThrottled, action)
    }

    fun throttleActionWithResult(
            delay: Long = defaultDelay,
            onThrottled: () -> Unit = {},
            action: () -> Unit
    ): Boolean {
        val currentTime = SystemClock.elapsedRealtime()
        val diff = currentTime - lastActionTime

        return if (diff >= delay) {
            lastActionTime = currentTime
            action.invoke()
            true
        } else {
            onThrottled.invoke()
            false
        }
    }
}

private const val APP_ACTION_THROTTLER_DELAY = 300L

private object AppActionThrottler : ActionThrottler(defaultDelay = APP_ACTION_THROTTLER_DELAY)

fun throttleAction(delay: Long = APP_ACTION_THROTTLER_DELAY, onThrottled: () -> Unit = {}, action: () -> Unit) {
    AppActionThrottler.throttleAction(delay, onThrottled, action)
}

fun throttleActionWithResult(
        delay: Long = APP_ACTION_THROTTLER_DELAY,
        onThrottled: () -> Unit = {},
        action: () -> Unit
): Boolean = AppActionThrottler.throttleActionWithResult(delay, onThrottled, action)

fun View.throttleClick(
        delay: Long = APP_ACTION_THROTTLER_DELAY,
        onThrottled: () -> Unit = {},
        action: () -> Unit
) {
    setOnClickListener { throttleAction(delay, onThrottled, action) }
}
