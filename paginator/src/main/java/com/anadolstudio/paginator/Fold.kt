package com.anadolstudio.paginator

import androidx.recyclerview.widget.RecyclerView

fun <E> PagingDataState<E>.fold(
        recyclerView: RecyclerView,
        onPageData: () -> Unit = {},
        onLoading: () -> Unit = {},
        onError: (error: Throwable) -> Unit = {},
        onEmptyData: () -> Unit = {},
        onContent: () -> Unit = {},
        onNotContent: () -> Unit = {},
        onNextPageLoading: () -> Unit = {},
        onNextPageError: (error: Throwable) -> Unit = {},
        onAllData: () -> Unit = {},
        onRefresh: () -> Unit = {},
        onRefreshError: () -> Unit = {},
) {
    recyclerView.post {
        when (this) {
            is PagingDataState.Loading -> onLoading.invoke()
            is PagingDataState.Error -> onError.invoke(error)
            is PagingDataState.Empty -> onEmptyData.invoke()
            is PagingDataState.Content.NextLoadingPage -> onNextPageLoading.invoke()
            is PagingDataState.Content.NextErrorPage -> onNextPageError.invoke(error)
            is PagingDataState.Content.Refresh -> onRefresh.invoke()
            is PagingDataState.Content.RefreshError -> onRefreshError.invoke()
            is PagingDataState.Content.PageData -> onPageData.invoke()
            is PagingDataState.Content.AllData -> onAllData.invoke()
            else -> Unit
        }

        if (this is PagingDataState.Content) {
            onContent.invoke()

        } else {
            onNotContent.invoke()
        }
    }
}
