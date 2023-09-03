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
    }

    var currentDelta: Int = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        currentDelta += dy

        if (abs(currentDelta) <= delta) return

        when {
            currentDelta < 0 -> onScrollToTop?.invoke()
            currentDelta > 0 -> onScrollToBottom?.invoke()
            recyclerView.scrollY == 0 -> onScrollToTop?.invoke()
        }

        currentDelta = 0
    }
}
