package com.anadolstudio.core.util.paginator

import com.anadolstudio.core.util.paginator.provider.PaginatorStateProvider

interface PagingState<E> : Paginator {
    fun fail(exception: Throwable)
    fun onNewPage(data: List<E>)
}

abstract class BaseState<E>(protected val provider: PaginatorStateProvider<E>) : PagingState<E> {
    override fun restart() = provider.toFirstLoadingState()
    override fun loadNewPage() = Unit
    override fun pullToRefresh() = Unit
    override fun refresh() = Unit
    override fun fail(exception: Throwable) = Unit
    override fun onNewPage(data: List<E>) = Unit
}

open class InitState<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {
    override fun loadNewPage() = provider.toFirstLoadingState()
}

open class FirstLoadingState<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {

    override fun fail(exception: Throwable) {
        provider.toFirstErrorState(exception = exception)
    }

    override fun onNewPage(data: List<E>) {
        if (data.isNotEmpty()) {
            provider.toPageDataState(data = data)
        } else {
            provider.toEmptyDataState()
        }
    }
}

open class EmptyDataState<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {

    override fun refresh() = provider.toFirstLoadingState()

}

open class FirstErrorState<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {

    override fun refresh() = provider.toFirstLoadingState()

}

open class PageDataState<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {

    override fun pullToRefresh() = provider.toRefreshState()

    override fun loadNewPage() = provider.toNewPageLoadingState()
}

open class NewPageLoadingState<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {

    override fun fail(exception: Throwable) = provider.toNewPageErrorState(exception)

    override fun pullToRefresh() = provider.toRefreshState()

    override fun onNewPage(data: List<E>) {
        if (data.isNotEmpty()) {
            provider.toPageDataState(data = data)
        } else {
            provider.toAllDataState()
        }
    }
}

open class NewPageErrorState<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {

    override fun refresh() = provider.toNewPageLoadingState()

    override fun pullToRefresh() = provider.toRefreshState()

}

open class RefreshState<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {

    override fun fail(exception: Throwable) = provider.toRefreshError(exception)

    override fun onNewPage(data: List<E>) {
        if (data.isNotEmpty()) {
            provider.toUpdateDataState(data = data)
        } else {
            provider.toEmptyDataState()
        }
    }

}

open class RefreshError<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {

    override fun loadNewPage() = provider.toNewPageLoadingState()

    override fun refresh() = provider.toRefreshState()

}

open class AllDataState<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {

    override fun pullToRefresh() = provider.toRefreshState()

}

open class UpdateDataState<E>(provider: PaginatorStateProvider<E>) : BaseState<E>(provider) {

    override fun pullToRefresh() = provider.toRefreshState()

    override fun loadNewPage() = provider.toNewPageLoadingState()

}



