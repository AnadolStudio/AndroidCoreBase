package com.anadolstudio.view.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING

open class SimpleScrollListener(
        private val onScrolled: ((RecyclerView, Int, Int) -> Unit)? = null,
        private val onScrollStateChanged: ((recyclerView: RecyclerView, newState: Int) -> Unit)? = null,
        private val onScrollStateIdle: (() -> Unit)? = null,
        private val onScrollStateSettling: (() -> Unit)? = null,
        private val onScrollStateDragging: (() -> Unit)? = null,
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        onScrolled?.invoke(recyclerView, dx, dy)
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        onScrollStateChanged?.invoke(recyclerView, newState)

        when (newState) {
            SCROLL_STATE_IDLE -> onScrollStateIdle?.invoke()
            SCROLL_STATE_DRAGGING -> onScrollStateSettling?.invoke()
            SCROLL_STATE_SETTLING -> onScrollStateDragging?.invoke()
            else -> Unit
        }
    }
}
