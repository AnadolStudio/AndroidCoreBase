package com.anadolstudio.core.util.paginator

sealed class PagingDataState<E> {

    class Loading<E> : PagingDataState<E>()

    class Error<E>(val error: Throwable) : PagingDataState<E>()

    class Empty<E> : PagingDataState<E>()

    sealed class Content<E> : PagingDataState<E>() {
        class NextLoadingPage<E> : Content<E>()
        class NextErrorPage<E>(val error: Throwable) : Content<E>()
        class Refresh<E> : Content<E>()
        class RefreshError<E> : Content<E>()
        class UpdateData<E>(val data: List<E>) : Content<E>()
        class AllData<E> : Content<E>()
        class PageData<E>(val data: List<E>) : Content<E>()
    }

}
