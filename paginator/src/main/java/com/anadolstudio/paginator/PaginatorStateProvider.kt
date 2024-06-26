package com.anadolstudio.paginator

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
