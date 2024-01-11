package com.anadolstudio.core.presentation.fragment.state_util.strategy.base

import android.view.View
import android.view.animation.Animation
import com.anadolstudio.core.util.common_extention.makeVisible
import com.anadolstudio.core.presentation.fragment.state_util.state.State

abstract class AbstractStateChangeStrategy<T : Enum<T>> : StateChangeStrategy<T> {

    protected open val viewsWithoutAnim: List<View> = emptyList()
    protected open val animation: Animation? = null

    open fun viewOnStateEnter(view: View) = view.makeVisible()

    abstract fun viewOnStateExit(view: View)

    override fun onStateEnter(state: State<T>, prevState: State<T>?) {
        state.viewsGroup.forEach(this::viewOnStateEnter)
        startAnimation(state)
    }

    protected open fun startAnimation(state: State<T>) {
        animation?.let { anim ->
            val viewsWithAnimation = mutableListOf<View>().apply {
                addAll(state.viewsGroup)
                removeAll(viewsWithoutAnim)
            }

            viewsWithAnimation.forEach { view -> view.startAnimation(anim) }
        }
    }

    override fun onStateExit(state: State<T>, nextState: State<T>?) {
        state.viewsGroup.forEach { view -> view.clearAnimation() }
        state.viewsGroup.forEach(this::viewOnStateExit)
    }
}

