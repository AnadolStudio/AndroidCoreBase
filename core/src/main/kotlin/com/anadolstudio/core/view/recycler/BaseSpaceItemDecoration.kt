package com.anadolstudio.core.view.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.anadolstudio.core.util.common.dpToPx

open class BaseSpaceItemDecoration(
        private val padding: Rect = Rect(MEDIUM_SPACE, MEDIUM_SPACE, MEDIUM_SPACE, MEDIUM_SPACE),
) : RecyclerView.ItemDecoration() {

    constructor(space: Int = MEDIUM_SPACE) : this(Rect(space, space, space, space))

    companion object {
        val SMALL_SPACE = 4.dpToPx()
        val MEDIUM_SPACE = 8.dpToPx()
        val EXTRA_MEDIUM_SPACE = 12.dpToPx()
        val MAIN_SPACE = 16.dpToPx()
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.set(padding)
    }

    class Horizontal(space: Int = MEDIUM_SPACE) : BaseSpaceItemDecoration(Rect(space, 0, space, 0))

    class Vertical(space: Int = MEDIUM_SPACE) : BaseSpaceItemDecoration(Rect(0, space, 0, space))

    class All(space: Int = MEDIUM_SPACE) : BaseSpaceItemDecoration(space)

}
