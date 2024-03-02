package com.anadolstudio.ui

import com.anadolstudio.ui.viewmodel.LceState

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

