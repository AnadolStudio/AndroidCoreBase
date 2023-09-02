package com.anadolstudio.core.util.common

import android.view.View

open class DoubleClick() {

    private var lastClickTime: Long = 0L

    fun onDoubleClickAction(delay: Long = DEFAULT_DELAY, onSimpleClick: (() -> Unit)? = null, onDoubleClick: () -> Unit) {
        val currentTime = System.currentTimeMillis()

        lastClickTime = when (lastClickTime + delay > currentTime) {
            true -> onDoubleClick.invoke().run { 0L }
            false -> onSimpleClick?.invoke().run { currentTime }
        }
    }
}

private const val DEFAULT_DELAY = 2000L

private object AppDoubleClick : DoubleClick()

fun View.doubleClick(delay: Long = DEFAULT_DELAY, onSimpleClick: (() -> Unit)? = null, onDoubleClick: () -> Unit) {
    setOnClickListener { AppDoubleClick.onDoubleClickAction(delay, onSimpleClick, onDoubleClick) }
}

fun doubleClickAction(delay: Long = DEFAULT_DELAY, onSimpleClick: (() -> Unit)? = null, onDoubleClick: () -> Unit) {
    AppDoubleClick.onDoubleClickAction(delay, onSimpleClick, onDoubleClick)
}
