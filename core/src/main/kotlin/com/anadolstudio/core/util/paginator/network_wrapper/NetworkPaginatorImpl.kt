package com.anadolstudio.core.util.paginator.network_wrapper

import com.anadolstudio.core.util.paginator.Paginator
import com.anadolstudio.core.util.paginator.PaginatorStateProvider
import com.anadolstudio.core.util.paginator.PagingViewController
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
