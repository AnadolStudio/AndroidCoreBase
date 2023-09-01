package com.anadolstudio.core.view.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.ScaleGestureDetector
import androidx.core.view.size
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anadolstudio.core.view.gesture.ScaleGesture
import kotlin.math.max

class GridRecyclerView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private companion object {
        const val ONE_POINTER = 1
        const val DEFAULT_COLUM_COUNT = 3
        const val DEFAULT_CACHE_SIZE = 50
    }

    private val gridLayoutManager =  GridLayoutManager(context, DEFAULT_COLUM_COUNT)
    private var onZoomIncreased: (() -> Unit)? = null
    private var onZoomDecreased: (() -> Unit)? = null

    private val scaleGestureDetector: ScaleGestureDetector by lazy {
        ScaleGestureDetector(
                context,
                ScaleGesture(
                        onIncrease = { onZoomIncreased?.invoke()},
                        onDecrease = { onZoomDecreased?.invoke()}
                )
        ).apply {
            isQuickScaleEnabled = false
        }
    }

    init {
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        setOnTouchListener { _, event ->
            return@setOnTouchListener when (event.pointerCount) {
                ONE_POINTER -> false
                else -> scaleGestureDetector.onTouchEvent(event)
            }
        }
        layoutManager = gridLayoutManager
        setItemViewCacheSize(DEFAULT_CACHE_SIZE)
        addItemDecoration(BaseSpaceItemDecoration.All(BaseSpaceItemDecoration.SMALL_SPACE))
    }

    fun setZoomListener(onZoomIncreased: (() -> Unit)?, onZoomDecreased: (() -> Unit)?) {
        this.onZoomIncreased = onZoomIncreased
        this.onZoomDecreased = onZoomDecreased
    }

    fun changeSpan(spanCount: Int) {
        val start = max(gridLayoutManager.findFirstVisibleItemPosition(), 0)
        var end = gridLayoutManager.findLastVisibleItemPosition()
        if (end < 0) {
            end = adapter?.itemCount ?: size
        }

        post {
            stopScroll()
            gridLayoutManager.spanCount = spanCount
            adapter?.notifyItemRangeChanged(start, end)
        }
    }
}
