package com.anadolstudio.ui.fragment.state_util.state

interface InitialState<T : Enum<T>> {

    fun apply(states: Array<out State<T>>) = Unit
}
