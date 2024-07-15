package com.google.android.material.appbar

import android.content.Context
import android.os.Parcelable
import android.view.View
import android.view.ViewOutlineProvider
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.viewbinding.ViewBinding
import com.anadolstudio.view.coordinator.AppBarBehaviorCallback
import com.anadolstudio.view.coordinator.BaseSavedState
import com.anadolstudio.view.coordinator.ChangeScrollStateListener
import com.anadolstudio.view.coordinator.ScrollState
import kotlin.math.abs

abstract class BaseAppBarBehavior<RestoreState : BaseSavedState, Binding : ViewBinding> : AppBarLayout.Behavior(), AppBarBehaviorCallback<RestoreState, Binding> {

    private var lastScrollingOffset: Int = Int.MAX_VALUE

    private var _binding: Binding? = null
    private val binding: Binding get() = requireNotNull(_binding)
    private var scrollStateListener: ChangeScrollStateListener? = null

    protected val context: Context get() = binding.root.context
    private var appBar: AppBarLayout? = null

    protected var scrollState: ScrollState = ScrollState.IDLE
        set(value) {
            if (field != value) {
                scrollStateListener?.invoke(value)
            }
            field = value
        }

    private val offsetListener: AppBarLayout.OnOffsetChangedListener by lazy {
        AppBarLayout.OnOffsetChangedListener { _, offset ->
            if (lastScrollingOffset != offset) {
                changeOffset(binding)
            }
        }
    }

    private var needWaitNestedFling: Boolean = false
    private var needCheckOnOffsetChangedListener: Boolean = false

    init {
        setDragCallback(
                object : DragCallback() {
                    override fun canDrag(appBarLayout: AppBarLayout): Boolean = canDrag(binding)
                }
        )
    }

    override fun onLayoutChild(
            parent: CoordinatorLayout,
            appBar: AppBarLayout,
            layoutDirection: Int
    ): Boolean {
        if (_binding == null) {
            initView(parent)
            this.appBar = appBar
        }
        appBar.outlineProvider = ViewOutlineProvider.BACKGROUND
        appBar.removeOnOffsetChangedListener(offsetListener)
        appBar.addOnOffsetChangedListener(offsetListener)

        return super.onLayoutChild(parent, appBar, layoutDirection)
    }

    private fun initView(parent: CoordinatorLayout) {
        _binding = initializeViewBinding(parent)
    }

    override fun onRestoreInstanceState(parent: CoordinatorLayout, appBar: AppBarLayout, state: Parcelable) {
        val baseState = state as? RestoreState
        super.onRestoreInstanceState(parent, appBar, baseState?.superState ?: state)

        if (state !is SavedState) return

        if (_binding == null) initView(parent)
        baseState?.let { onRestoreState(binding, it) }
    }

    override fun onSaveInstanceState(
            parent: CoordinatorLayout, abl: AppBarLayout
    ): Parcelable? = super.onSaveInstanceState(parent, abl)?.let { supperState ->
        onSaveState(binding, supperState)
    }

    override fun onStartNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            appBar: AppBarLayout,
            directTargetChild: View,
            target: View,
            axes: Int,
            type: Int
    ): Boolean {
        onScrollStarted(binding)

        return super.onStartNestedScroll(coordinatorLayout, appBar, directTargetChild, target, axes, type)
    }

    override fun onNestedPreScroll(
            coordinatorLayout: CoordinatorLayout,
            appBar: AppBarLayout,
            target: View,
            dx: Int,
            dy: Int,
            consumed: IntArray,
            type: Int
    ) {
        if (appBar.isCollapsed() && !isAbsDyBiggerInnerScroll(dy, target)) {
            return super.onNestedPreScroll(coordinatorLayout, appBar, target, dx, dy, consumed, type)
        }

        scrollState = ScrollState.SCROLLING
        validateScrolling()

        super.onNestedPreScroll(coordinatorLayout, appBar, target, dx, dy, consumed, type)
    }

    override fun onNestedPreFling(
            coordinatorLayout: CoordinatorLayout,
            child: AppBarLayout,
            target: View,
            velocityX: Float,
            velocityY: Float
    ): Boolean {
        needWaitNestedFling = true
        onFlingStarted(binding)

        return super.onNestedPreFling(coordinatorLayout, child, target, velocityX, velocityY)
    }

    override fun onNestedFling(
            coordinatorLayout: CoordinatorLayout,
            child: AppBarLayout,
            target: View,
            velocityX: Float,
            velocityY: Float,
            consumed: Boolean
    ): Boolean {
        onFling(binding)

        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    override fun onStopNestedScroll(
            coordinatorLayout: CoordinatorLayout,
            appBar: AppBarLayout,
            target: View,
            type: Int
    ) {
        super.onStopNestedScroll(coordinatorLayout, appBar, target, type)

        if (type == ViewCompat.TYPE_TOUCH && !needWaitNestedFling) {
            onScrollStoped(binding)
        } else if (type == ViewCompat.TYPE_NON_TOUCH && needWaitNestedFling) {
            onFlingFinished(binding)
            needWaitNestedFling = false
        }
    }

    override fun onScrollStarted(binding: Binding) {
        scrollState = ScrollState.SCROLLING
    }

    override fun onFlingFinished(parent: CoordinatorLayout, appBar: AppBarLayout) {
        super.onFlingFinished(parent, appBar)
        onFlingFinished(binding)
    }

    override fun onScrolling(binding: Binding) {
        scrollState = ScrollState.SCROLLING
        changeOffset(binding)
    }

    private fun validateScrolling() {

        if (lastScrollingOffset == topAndBottomOffset) return
        lastScrollingOffset = topAndBottomOffset

        onScrolling(binding)
    }

    override fun onScrollStoped(binding: Binding) {
        scrollState = ScrollState.IDLE
    }

    override fun onFlingStarted(binding: Binding) {
        scrollState = ScrollState.SCROLLING
    }

    override fun onFling(binding: Binding) {
        validateScrolling()
    }

    override fun onFlingFinished(binding: Binding) {
        onScrollStoped(binding)
        needCheckOnOffsetChangedListener = false
    }

    override fun setOnChangeScrollStateListener(listener: ChangeScrollStateListener?) {
        scrollStateListener = listener
    }

    private fun isAbsDyBiggerInnerScroll(dy: Int, target: View): Boolean = abs(dy) > target.scrollY

    private fun AppBarLayout.isCollapsed(): Boolean = abs(topAndBottomOffset) == totalScrollRange

}
