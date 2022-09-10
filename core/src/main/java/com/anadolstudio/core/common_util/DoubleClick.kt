package com.anadolstudio.core.common_util

import android.view.View

open class DoubleClick(private val delay: Long = DEFAULT_DELAY) {

    private var lastClickTime: Long = 0L

    fun onDoubleClickAction(onSimpleClick: (() -> Unit)? = null, onDoubleClick: () -> Unit) {
        val currentTime = System.currentTimeMillis()

        lastClickTime = when (lastClickTime + delay > currentTime) {
            true -> onDoubleClick.invoke().run { 0L }
            false -> onSimpleClick?.invoke().run { currentTime }
        }
    }
}

private const val DEFAULT_DELAY = 2000L

fun View.doubleClick(delay: Long = DEFAULT_DELAY, onSimpleClick: (() -> Unit)? = null, onDoubleClick: () -> Unit) {
    val action = DoubleClick(delay)
    setOnClickListener { action.onDoubleClickAction(onSimpleClick, onDoubleClick) }
}
