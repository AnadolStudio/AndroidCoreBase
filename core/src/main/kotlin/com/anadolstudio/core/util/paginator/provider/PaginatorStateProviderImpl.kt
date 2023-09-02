package com.anadolstudio.core.util.paginator.provider

import com.anadolstudio.core.util.paginator.InitState
import com.anadolstudio.core.util.paginator.PagingState
import com.anadolstudio.core.util.paginator.PagingViewController
import io.reactivex.Single

class PaginatorStateProviderImpl<E>(
        viewController: PagingViewController<E>,
        requestFactory: (Int) -> Single<List<E>>,
        firstPageNumber: Int
) : AbstractPaginatorStateProvider<E>(viewController, requestFactory, firstPageNumber) {

    override var currentState: PagingState<E> = InitState(this)
}
