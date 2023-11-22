package com.anadolstudio.core.presentation

import androidx.recyclerview.widget.RecyclerView
import com.anadolstudio.core.util.paginator.PagingDataState
import com.anadolstudio.core.viewmodel.LceState

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

fun <Data> LceState<Data>.fold(
        onContent: ((Data) -> Unit),
        onLoading: (() -> Unit) = {},
        onEmpty: (() -> Unit) = {},
        onNotContent: (() -> Unit) = {},
        onError: ((Throwable) -> Unit) = {},
) = when (this) {
    is LceState.Content -> {
        if (data is List<*> && data.isEmpty()) {
            onNotContent.invoke()
            onEmpty.invoke()
        } else {
            onContent.invoke(data)
        }
    }
    is LceState.Loading -> {
        onLoading.invoke()
        onNotContent.invoke()
    }
    is LceState.Error -> {
        onError.invoke(error)
        onNotContent.invoke()
    }
}

fun <Data, C : Collection<Data>> C.fold(
        onContent: ((C) -> Unit),
        onEmpty: (() -> Unit) = { },
        onEach: ((isContent: Boolean) -> Unit) = { },
) = when (isNotEmpty()) {
    true -> onContent.invoke(this)
    false -> onEmpty.invoke()
}.also {
    onEach.invoke(isNotEmpty())
}

