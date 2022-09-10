package com.anadolstudio.core.common_util

import android.view.View

open class DoubleClick(private val delay: Long = DEFAULT_DELAY) {

    private var lastClickTime: Long = 0L

    fun onDoubleClickAction(onSimpleClick: (() -> Unit)? = null, onDoubleClick: () -> Unit) {
        val currentTime = System.currentTimeMillis()
        val wasDoubleClick = lastClickTime + delay > currentTime

        when (wasDoubleClick) {
            true -> onDoubleClick.invoke()
            false -> onSimpleClick?.invoke()
        }

        lastClickTime = if (wasDoubleClick) 0L else currentTime
    }
}

private const val DEFAULT_DELAY = 2000L

fun View.doubleClick(delay: Long = DEFAULT_DELAY, onSimpleClick: (() -> Unit)? = null, onDoubleClick: () -> Unit) {
    val action = DoubleClick(delay)
    setOnClickListener { action.onDoubleClickAction(onSimpleClick, onDoubleClick) }
}
