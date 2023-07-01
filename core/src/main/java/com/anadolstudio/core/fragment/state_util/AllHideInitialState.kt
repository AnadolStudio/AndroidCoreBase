package com.anadolstudio.core.fragment.state_util

import com.anadolstudio.core.common_extention.makeGone

class AllHideInitialState<T : Enum<T>> : InitialState<T> {

    override fun apply(states: Array<out State<T>>) = states.forEach { state ->
        state.viewsGroup.forEach { it.makeGone() }
    }
}
