package com.anadolstudio.core.util.paginator.provider

import com.anadolstudio.core.util.paginator.AllDataState
import com.anadolstudio.core.util.paginator.EmptyDataState
import com.anadolstudio.core.util.paginator.FirstErrorState
import com.anadolstudio.core.util.paginator.FirstLoadingState
import com.anadolstudio.core.util.paginator.InitState
import com.anadolstudio.core.util.paginator.NewPageErrorState
import com.anadolstudio.core.util.paginator.NewPageLoadingState
import com.anadolstudio.core.util.paginator.PageDataState
import com.anadolstudio.core.util.paginator.PagingState
import com.anadolstudio.core.util.paginator.PagingViewController
import com.anadolstudio.core.util.paginator.RefreshError
import com.anadolstudio.core.util.paginator.RefreshState
import com.anadolstudio.core.util.paginator.UpdateDataState
import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class AbstractPaginatorStateProvider<E>(
        protected val viewController: PagingViewController<E>,
        protected val requestFactory: (Int) -> Single<List<E>>,
        protected val firstPageNumber: Int
) : PaginatorStateProvider<E> {

    private var disposable: Disposable? = null
    protected var currentPageNumber: Int = firstPageNumber

    protected abstract var currentState: PagingState<E>

    protected fun loadPage(page: Int) {
        disposable?.dispose()
        disposable = requestFactory.invoke(page).subscribe(
                { data -> onNewPage(data) },
                { fail(it) }
        )
    }

    protected fun loadToPage(page: Int) {
        disposable?.dispose()

        var single = requestFactory.invoke(0)

        for (i in 1 until page) {
            single = single.zipWith(requestFactory.invoke(i)) { first, second -> first + second }
        }

        disposable = single.subscribe(
                { data -> onNewPage(data) },
                { fail(it) }
        )
    }

    override fun restart() = currentState.restart()
    override fun loadNewPage() = currentState.loadNewPage()
    override fun pullToRefresh() = currentState.pullToRefresh()
    override fun refresh() = currentState.refresh()
    override fun fail(exception: Throwable) = currentState.fail(exception)
    override fun onNewPage(data: List<E>) = currentState.onNewPage(data)

    override fun toInitState() {
        InitState(this)
    }

    override fun toFirstLoadingState() {
        viewController.onLoading()
        currentState = FirstLoadingState(this)

        currentPageNumber = firstPageNumber
        loadPage(currentPageNumber)
    }

    override fun toFirstErrorState(exception: Throwable) {
        viewController.onError(exception)
        currentState = FirstErrorState(this)
    }

    override fun toPageDataState(data: List<E>) {
        viewController.onPageData(data)
        currentState = PageDataState(this)
    }

    override fun toEmptyDataState() {
        viewController.onEmptyData()
        currentState = EmptyDataState(this)
    }

    override fun toNewPageLoadingState() {
        viewController.onNextPageLoading()
        currentState = NewPageLoadingState(this)

        loadPage(++currentPageNumber)
    }

    override fun toNewPageErrorState(exception: Throwable) {
        viewController.onNextPageError(exception)

        currentState = NewPageErrorState(this)
    }

    override fun toAllDataState() {
        viewController.onAllData()

        currentState = AllDataState(this)
    }

    override fun toRefreshState() {
        viewController.onRefresh()

        currentPageNumber = firstPageNumber
        loadPage(currentPageNumber)
        currentState = RefreshState(this)
    }

    override fun toRefreshError(exception: Throwable) {
        viewController.onRefreshError(exception)

        currentState = RefreshError(this)
    }

    override fun toUpdateDataState(data: List<E>) {
        viewController.onUpdateData(data)

        currentState = UpdateDataState(this)
    }

}
