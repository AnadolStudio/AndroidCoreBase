package com.anadolstudio.paginator

import io.reactivex.Single

abstract class BasePaginatorStateProviderImpl<E, T : List<E>>(
        viewController: PagingViewController<E>,
        requestFactory: (Int) -> Single<T>,
        firstPageNumber: Int
) : AbstractPaginatorStateProvider<E, T>(viewController, requestFactory, firstPageNumber) {

    override fun mapToOutputData(single: Single<T>): Single<List<E>> = single.map { it }

}
