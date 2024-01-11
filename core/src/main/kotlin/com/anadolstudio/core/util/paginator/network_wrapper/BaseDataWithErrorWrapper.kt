package com.anadolstudio.core.util.paginator.network_wrapper

abstract class BaseDataWithErrorWrapper<T>(
        private val content: List<T>,
        private val loadingError: Throwable? = null,
        private val hasNext: Boolean? = null
) : DataWithErrorWrapper<T> {

    override fun getData(): List<T> = content

    override fun getError(): Throwable? = loadingError

    override fun hasNext(): Boolean = hasNext ?: true
}
