package com.anadolstudio.core.common_util

import android.view.View

open class DoubleClick(private val delay: Long = DEFAULT_DELAY) {

    private var lastClickTime: Long = 0L

    fun onDoubleClickAction(onSimpleClick: (() -> Unit)? = null, onDoubleClick: () -> Unit) {
        val currentTime = System.currentTimeMillis()

        when (lastClickTime + delay > currentTime) {
            true -> onDoubleClick.invoke()
            false -> onSimpleClick?.invoke()
        }

        lastClickTime = currentTime
    }
}

private const val DEFAULT_DELAY = 2000L

fun doubleClickAction(
    delay: Long = DEFAULT_DELAY,
    onSimpleClick: (() -> Unit)? = null,
    onDoubleClick: () -> Unit
) {
    DoubleClick(delay).onDoubleClickAction(onSimpleClick, onDoubleClick)
}

fun View.doubleClick(delay: Long = DEFAULT_DELAY, onSimpleClick: (() -> Unit)? = null, onDoubleClick: () -> Unit) {
    setOnClickListener { doubleClickAction(delay, onSimpleClick, onDoubleClick) }
}
