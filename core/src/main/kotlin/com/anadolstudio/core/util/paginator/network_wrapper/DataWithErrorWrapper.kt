package com.anadolstudio.core.util.paginator.network_wrapper

interface DataWithErrorWrapper<T> {

    fun getData(): List<T>

    fun getError(): Throwable?

    fun hasNext(): Boolean

}
