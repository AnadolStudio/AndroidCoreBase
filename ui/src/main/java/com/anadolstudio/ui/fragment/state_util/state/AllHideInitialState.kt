package com.anadolstudio.ui.fragment.state_util.state

import com.anadolstudio.utils.util.extentions.makeGone

class AllHideInitialState<T : Enum<T>> : InitialState<T> {

    override fun apply(states: Array<out State<T>>) = states.forEach { state ->
        state.viewsGroup.forEach { it.makeGone() }
    }
}
