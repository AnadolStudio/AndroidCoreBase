package com.anadolstudio.core.util.paginator.provider

import com.anadolstudio.core.util.paginator.PagingState

interface PaginatorStateProvider<E> : PagingState<E> {

    fun toInitState()
    fun toFirstLoadingState()
    fun toFirstErrorState(exception: Throwable)
    fun toPageDataState(data: List<E>)
    fun toEmptyDataState()
    fun toNewPageLoadingState()
    fun toNewPageErrorState(exception: Throwable)
    fun toAllDataState()
    fun toRefreshState()
    fun toRefreshError(exception: Throwable)
    fun toUpdateDataState(data: List<E>)
}
