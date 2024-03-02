package com.anadolstudio.paginator.network_wrapper

import com.anadolstudio.paginator.AbstractPaginatorStateProvider
import com.anadolstudio.paginator.EmptyDataState
import com.anadolstudio.paginator.InitState
import com.anadolstudio.paginator.PagingState
import com.anadolstudio.paginator.PagingViewController
import io.reactivex.Single

class NetworkWrapperPaginatorStateProviderImpl<Data, Wrapper : DataWithErrorWrapper<Data>>(
        requestFactory: (Int) -> Single<Wrapper>,
        viewController: PagingViewController<Data>,
        firstPageNumber: Int
) : AbstractPaginatorStateProvider<Data, Wrapper>(viewController, requestFactory, firstPageNumber) {

    override var currentState: PagingState<Data> = InitState(this)

    override fun mapToOutputData(single: Single<Wrapper>): Single<List<Data>> = single
            .doAfterSuccess { wrapper ->
                val isEmptyState = currentState is EmptyDataState
                val hasNextData = wrapper.getData().isNotEmpty() && wrapper.hasNext()

                if (wrapper.getError() == null && !isEmptyState && !hasNextData) {
                    toAllDataState()
                }
            }
            .map { wrapper ->
                val data = wrapper.getData()
                val error = wrapper.getError()
                when {
                    error != null -> throw error
                    data.isNotEmpty() -> data
                    else -> emptyList()
                }
            }
}
