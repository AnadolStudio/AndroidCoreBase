package com.anadolstudio.core.view.common

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View

class TouchEventListenerContainer : BaseListenersContainer<View.OnTouchListener>(), View.OnTouchListener {

    private var mainTouchEventListener: View.OnTouchListener? = null

    fun setMainTouchEventListener(listener: View.OnTouchListener?) {
        mainTouchEventListener = listener
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean = mainTouchEventListener
            ?.onTouch(v, event).also { listeners.forEach { it.onTouch(v, event) } }
            ?: listeners.any { it.onTouch(v, event) }

}
