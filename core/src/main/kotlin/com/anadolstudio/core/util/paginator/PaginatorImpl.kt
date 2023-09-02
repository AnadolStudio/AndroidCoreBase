package com.anadolstudio.core.util.paginator

import com.anadolstudio.core.util.paginator.provider.PaginatorStateProvider
import com.anadolstudio.core.util.paginator.provider.PaginatorStateProviderImpl
import io.reactivex.Single

open class PaginatorImpl<E : Any>(
        protected val requestFactory: (Int) -> Single<List<E>>,
        protected val viewController: PagingViewController<E>,
        protected val firstPageNumber: Int = DEFAULT_FIRST_PAGE_NUMBER
) : Paginator {

    companion object {
        const val DEFAULT_FIRST_PAGE_NUMBER = 1
    }

    protected open val provider: PaginatorStateProvider<E> = PaginatorStateProviderImpl(
            viewController,
            requestFactory,
            firstPageNumber
    )

    override fun refresh() = provider.refresh()
    override fun restart() = provider.restart()
    override fun pullToRefresh() = provider.pullToRefresh()
    override fun loadNewPage() = provider.loadNewPage()
}
