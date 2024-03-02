package com.anadolstudio.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.anadolstudio.utils.util.extentions.onTrue
import com.anadolstudio.view.common.TouchEventListenerContainer

class TouchFrameLayout @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val touchListeners = TouchEventListenerContainer()
    private val dispatchTouchListeners = TouchEventListenerContainer()

    init {
        initView()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        setOnTouchListener(touchListeners)
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val event = ev ?: return super.dispatchTouchEvent(null)

        return dispatchTouchListeners.onTouch(this, event).not().onTrue {
            super.dispatchTouchEvent(event)
        }
    }
}
