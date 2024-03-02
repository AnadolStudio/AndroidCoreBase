package com.anadolstudio.paginator

import io.reactivex.Single
import io.reactivex.disposables.Disposable

abstract class AbstractPaginatorStateProvider<OutputData, InputData>(
        protected val viewController: PagingViewController<OutputData>,
        protected val requestFactory: (Int) -> Single<InputData>,
        protected val firstPageNumber: Int
) : PaginatorStateProvider<OutputData> {

    private var disposable: Disposable? = null
    protected var currentPageNumber: Int = firstPageNumber

    protected abstract var currentState: PagingState<OutputData>

    protected fun loadPage(page: Int) {
        disposable?.dispose()
        disposable = requestFabricSubscribe(requestFactory.invoke(page))
    }

    private fun requestFabricSubscribe(single: Single<InputData>) = mapToOutputData(single).subscribe(
            { data -> onNewPage(data) },
            { fail(it) }
    )

    abstract fun mapToOutputData(single: Single<InputData>): Single<List<OutputData>>

    override fun restart() = currentState.restart()
    override fun loadNewPage() = currentState.loadNewPage()
    override fun pullToRefresh() = currentState.pullToRefresh()
    override fun refresh() = currentState.refresh()
    override fun fail(exception: Throwable) = currentState.fail(exception)
    override fun onNewPage(data: List<OutputData>) = currentState.onNewPage(data)

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

    override fun toPageDataState(data: List<OutputData>) {
        currentPageNumber++
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

        loadPage(currentPageNumber + 1)
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

    override fun toUpdateDataState(data: List<OutputData>) {
        viewController.onUpdateData(data)

        currentState = UpdateDataState(this)
    }

}
