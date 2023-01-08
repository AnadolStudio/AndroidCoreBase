package com.anadolstudio.core.view.snackbar

import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.snackbar.BaseTransientBottomBar

class NoSwipeBehavior: BaseTransientBottomBar.Behavior() {

    override fun canSwipeDismissView(child: View): Boolean  = false

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: View, event: MotionEvent): Boolean = false

}
