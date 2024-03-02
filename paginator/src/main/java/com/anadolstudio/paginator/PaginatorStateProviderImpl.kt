package com.anadolstudio.paginator

import io.reactivex.Single

class PaginatorStateProviderImpl<E>(
        viewController: PagingViewController<E>,
        requestFactory: (Int) -> Single<List<E>>,
        firstPageNumber: Int
) : BasePaginatorStateProviderImpl<E, List<E>>(viewController, requestFactory, firstPageNumber) {

    override var currentState: PagingState<E> = InitState(this)

    override fun mapToOutputData(single: Single<List<E>>): Single<List<E>> = single
}
