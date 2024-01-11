package com.anadolstudio.core.view.recycler

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import androidx.core.view.size
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anadolstudio.core.util.common_extention.onTrue
import com.anadolstudio.core.view.common.TouchEventListenerContainer
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

    private val gridLayoutManager = GridLayoutManager(context, DEFAULT_COLUM_COUNT)
    private val touchListeners = TouchEventListenerContainer()
    private val dispatchTouchListeners = TouchEventListenerContainer()
    private var onZoomIncreased: (() -> Unit)? = null
    private var onZoomDecreased: (() -> Unit)? = null

    private val scaleGestureDetector: ScaleGestureDetector by lazy {
        ScaleGestureDetector(
                context,
                ScaleGesture(
                        onIncrease = { onZoomIncreased?.invoke() },
                        onDecrease = { onZoomDecreased?.invoke() }
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
        setOnTouchListener(touchListeners)
        touchListeners.setMainTouchEventListener { _, event ->
            when (event.pointerCount) {
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

    fun addTouchListener(listener: OnTouchListener) {
        touchListeners.add(listener)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun addTouchListener(listener: (v: View, event: MotionEvent) -> Boolean) {
        touchListeners.add { v, event -> listener.invoke(v, event) }
    }

    fun addDispatchTouchListener(listener: OnTouchListener) {
        dispatchTouchListeners.add(listener)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun addDispatchTouchListener(listener: (v: View, event: MotionEvent) -> Boolean) {
        dispatchTouchListeners.add { v, event -> listener.invoke(v, event) }
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val event = ev ?: return super.dispatchTouchEvent(null)

        return dispatchTouchListeners.onTouch(this, event).not().onTrue {
            super.dispatchTouchEvent(event)
        }
    }
}
