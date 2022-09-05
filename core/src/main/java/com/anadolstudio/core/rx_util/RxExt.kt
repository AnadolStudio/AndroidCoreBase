package com.anadolstudio.core.rx_util

import io.reactivex.Single

fun <T : Any> singleFrom(action: () -> T): Single<T> = Single.create { emitter ->
    try {
        val result = action.invoke()
        emitter.onSuccess(result)
    } catch (ex: Exception) {
        emitter.onError(ex)
    }
}
