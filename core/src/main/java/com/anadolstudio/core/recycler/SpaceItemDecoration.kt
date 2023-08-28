package com.anadolstudio.core.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anadolstudio.core.common_util.dpToPx

class SpaceItemDecoration(private val space: Int = 0) : RecyclerView.ItemDecoration() {

    companion object {
        val SMALL_SPACE = 4.dpToPx()
        val MEDIUM_SPACE = 8.dpToPx()
        val MAIN_SPACE = 16.dpToPx()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect[space, space, space] = space
    }
}
