package com.anadolstudio.core.view.common

open class BaseListenersContainer<E>(protected val listeners: MutableList<E> = mutableListOf()) {

    fun add(listener: E): Boolean = listeners.add(listener)

    fun remove(listener: E): Boolean = listeners.remove(listener)

}
