package com.anadolstudio.ui.fragment.state_util.strategy.base

import com.anadolstudio.ui.fragment.state_util.state.State

interface StateChangeStrategy<T : Enum<T>> {

    fun onStateEnter(state: State<T>, prevState: State<T>?) = Unit

    fun onStateExit(state: State<T>, nextState: State<T>?) = Unit
}
