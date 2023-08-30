package com.anadolstudio.core.presentation

interface Renderable {

    val stateMap: MutableMap<String, Int>

    fun <State : Any> State.render(key: String, render: State.() -> Unit) {
        if (stateMap[key] == hashCode()) return

        stateMap[key] = hashCode()

        render.invoke(this)
    }

}
