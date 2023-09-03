package com.anadolstudio.core.view.recycler

import androidx.recyclerview.widget.RecyclerView
import com.anadolstudio.core.util.common.dpToPx
import kotlin.math.abs

class ScrollListener(
        val delta: Int = DELTA,
        val onScrollToTop: (() -> Unit)? = null,
        val onScrollToBottom: (() -> Unit)? = null
) : RecyclerView.OnScrollListener() {

    private companion object {
        val DELTA = 128.dpToPx()
        const val TOP = -1
    }

    private var currentDelta: Int = 0

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            currentDelta = 0
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        currentDelta += dy
        val currentDeltaIsValid = abs(currentDelta) >= delta

        when {
            currentDeltaIsValid && currentDelta < 0 -> onScrollToTop?.invoke()
            currentDeltaIsValid && currentDelta > 0 -> onScrollToBottom?.invoke()
            !recyclerView.canScrollVertically(TOP) -> onScrollToTop?.invoke()
        }

        if (currentDeltaIsValid) {
            currentDelta = 0
        }
    }
}
