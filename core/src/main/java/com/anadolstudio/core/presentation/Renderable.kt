package com.anadolstudio.core.presentation

interface Renderable {

    val stateMap: MutableMap<String, Int>

    fun <State>render(pair: Pair<String, State>, render: State.() -> Unit) {
        val key = pair.first
        val state = pair.second
        val hashCode = state.hashCode()

        if (stateMap[key] == hashCode) return

        stateMap[key] = hashCode

        render.invoke(state)
    }

}
