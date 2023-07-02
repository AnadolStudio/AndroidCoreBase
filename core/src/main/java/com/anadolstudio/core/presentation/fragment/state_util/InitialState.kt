package com.anadolstudio.core.presentation.fragment.state_util

interface InitialState<T : Enum<T>> {

    fun apply(states: Array<out State<T>>) {}
}
