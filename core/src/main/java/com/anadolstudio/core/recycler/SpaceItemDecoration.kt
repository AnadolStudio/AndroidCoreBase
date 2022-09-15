package com.anadolstudio.core.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val space: Int = 0) : RecyclerView.ItemDecoration() {

    companion object {
        val NORMAL_SPACE = 8
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect[space, space, space] = space
    }
}
