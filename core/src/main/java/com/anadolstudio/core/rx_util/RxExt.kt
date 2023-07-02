package com.anadolstudio.core.rx_util

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T : Any> singleFrom(action: () -> T): Single<T> = Single.create { emitter ->
    try {
        val result = action.invoke()
        emitter.onSuccess(result)
    } catch (ex: Exception) {
        emitter.onError(ex)
    }
}

fun <T : Any> singleBy(action: SingleEmitter<T>.() -> Unit): Single<T> = Single.create { emitter ->
    try {
        action.invoke(emitter)
    } catch (ex: Exception) {
        emitter.onError(ex)
    }
}

fun <T : Any> quickSingleFrom(action: () -> T): Single<T> = Single.create<T> { emitter ->
    try {
        val result = action.invoke()
        emitter.onSuccess(result)
    } catch (ex: Exception) {
        emitter.onError(ex)
    }
}.schedulersIoToMain()

fun <T> Single<T>.schedulersIoToMain(): Single<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.smartSubscribe(
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onFinally: (() -> Unit)? = null
): Disposable = this.subscribe(
        { data ->
            onSuccess?.invoke(data)
            onFinally?.invoke()
        },
        { error ->
            onError?.invoke(error)
            onFinally?.invoke()
        }
)

fun <T> Observable<T>.schedulersIoToMain(): Observable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.smartSubscribe(
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        onFinally: (() -> Unit)? = null
): Disposable = this.subscribe(
        { data ->
            onSuccess?.invoke(data)
            onFinally?.invoke()
        },
        { error ->
            onError?.invoke(error)
            onFinally?.invoke()
        },
        { onComplete?.invoke() }
)

fun <T> Flowable<T>.schedulersIoToMain(): Flowable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.schedulersIoToMain(): Completable = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.smartSubscribe(
        onComplete: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onFinally: (() -> Unit)? = null
): Disposable = this.subscribe(
        {
            onComplete?.invoke()
            onFinally?.invoke()
        },
        { error ->
            onError?.invoke(error)
            onFinally?.invoke()
        }
)

fun <T> Maybe<T>.schedulersIoToMain(): Maybe<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

