package com.anadolstudio.core.presentation.fragment.state_util.state

import com.anadolstudio.core.util.common_extention.makeGone

class AllHideInitialState<T : Enum<T>> : InitialState<T> {

    override fun apply(states: Array<out State<T>>) = states.forEach { state ->
        state.viewsGroup.forEach { it.makeGone() }
    }
}
