package com.anadolstudio.paginator.network_wrapper

import com.anadolstudio.paginator.Paginator
import com.anadolstudio.paginator.PaginatorStateProvider
import com.anadolstudio.paginator.PagingViewController
import io.reactivex.Single

open class NetworkPaginatorImpl<Data : Any, Wrapper : DataWithErrorWrapper<Data>>(
        requestFactory: (Int) -> Single<Wrapper>,
        viewController: PagingViewController<Data>,
        firstPageNumber: Int = Paginator.DEFAULT_FIRST_PAGE_NUMBER
) : Paginator {

    protected open val provider: PaginatorStateProvider<Data> = NetworkWrapperPaginatorStateProviderImpl(
            requestFactory,
            viewController,
            firstPageNumber
    )

    override fun refresh() = provider.refresh()
    override fun restart() = provider.restart()
    override fun pullToRefresh() = provider.pullToRefresh()
    override fun loadNewPage() = provider.loadNewPage()
}
