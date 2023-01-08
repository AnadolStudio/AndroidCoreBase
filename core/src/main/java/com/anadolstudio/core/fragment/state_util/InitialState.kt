package com.anadolstudio.core.fragment.state_util

interface InitialState<T : Enum<T>> {

    fun apply(states: Array<out State<T>>) {}
}
