package com.anadolstudio.core.util.paginator

import com.anadolstudio.core.presentation.ContentableState

sealed class PagingDataState<E> : ContentableState {

    override val isShimmers: Boolean get() = this is Loading
    override val isFullScreenLoading: Boolean get() = this is Content.Refresh
    override val isEmpty: Boolean get() = this is Empty
    override val isError: Boolean get() = this is Error
    override val isContent: Boolean get() = this is Content
    override val getError: Throwable? get() = if (this is Error) this.error else null

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
