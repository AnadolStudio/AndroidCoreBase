package com.anadolstudio.view.recycler

class SimpleUpAndDownScrollListener(
        private val onScrollDown: (() -> Unit)? = null,
        private val onScrollUp: (() -> Unit)? = null
) : SimpleScrollListener(
        onScrolled = { _, _, dy ->

            if (dy > 0) { // scrollDown
                onScrollDown?.invoke()
            } else {
                onScrollUp?.invoke()
            }
        }
)
