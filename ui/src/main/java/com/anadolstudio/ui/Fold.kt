package com.anadolstudio.ui

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

