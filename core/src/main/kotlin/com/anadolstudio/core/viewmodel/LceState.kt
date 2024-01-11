package com.anadolstudio.core.viewmodel

sealed class LceState<Data> {
    val contentOrNull: Data? get() = if (this is Content) data else null

    class Loading<Data> : LceState<Data>()
    class Content<Data>(val data: Data) : LceState<Data>()
    class Error<Data>(val error: Throwable) : LceState<Data>()
}
