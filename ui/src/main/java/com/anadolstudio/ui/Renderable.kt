package com.anadolstudio.ui

interface Renderable {

    val stateMap: MutableMap<String, Any?>

    fun <State : Any?> State.render(key: String, render: State.() -> Unit) {
        if (stateMap.containsKey(key) && !hasChanges(stateMap[key], this)) return

        stateMap[key] = this

        render.invoke(this)
    }

    fun <State : Any?> State.render(
            primaryKey: String,
            vararg dependentStates: Pair<String, Any?>,
            render: State.() -> Unit
    ) {
        val dependentStateMap = dependentStates.toMap().toMutableMap()
        dependentStateMap[primaryKey] = this

        if (dependentStateMap.all { (key, item) -> stateMap.containsKey(key) && !hasChanges(stateMap[key], item) }) return

        dependentStateMap.forEach { (key, item) -> stateMap[key] = item }

        render.invoke(this)
    }

    private fun areSame(old: Any?, new: Any?): Boolean = old.hashCode() == new.hashCode()

    private fun equals(old: Any?, new: Any?): Boolean = old == new

    private fun hasChanges(old: Any?, new: Any?) = !areSame(old, new) || !equals(old, new)
}
