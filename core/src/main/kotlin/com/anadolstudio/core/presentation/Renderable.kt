package com.anadolstudio.core.presentation

interface Renderable {

    val stateMap: MutableMap<String, Int>

    fun <State : Any> State.render(key: String, render: State.() -> Unit) {
        if (stateMap[key] == hashCode()) return

        stateMap[key] = hashCode()

        render.invoke(this)
    }

    fun <State : Any> State.render(primaryKey: String, vararg dependentStates: Pair<String, Any?>, render: State.() -> Unit) {
        val dependentStateMap = dependentStates.toMap()
                .mapValues { it.hashCode() }
                .toMutableMap()
        dependentStateMap[primaryKey] = this.hashCode()

        if (dependentStateMap.all { (key, hashCode) -> stateMap[key] == hashCode }) return

        dependentStateMap.forEach { (key, hashCode) -> stateMap[key] = hashCode }

        render.invoke(this)
    }

}
