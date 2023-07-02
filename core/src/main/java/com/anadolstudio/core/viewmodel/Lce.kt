package com.anadolstudio.core.viewmodel

sealed class Lce<Data> {

    class Loading<Data> : Lce<Data>()

    sealed class Content<Data> : Lce<Data>() {
        class Empty<Data> : Content<Data>()
        class Data<Data>(val data: Data) : Content<Data>()
    }

    class Error<Data>(val error: Throwable) : Lce<Data>()
}
