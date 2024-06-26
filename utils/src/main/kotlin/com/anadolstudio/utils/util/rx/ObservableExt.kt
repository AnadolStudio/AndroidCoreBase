package com.anadolstudio.utils.util.rx

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.schedulersIoToMain(): Observable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

@CheckReturnValue
fun <T> Observable<T>.smartSubscribe(
        isSchedulersIoToMain: Boolean = true,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        onFinally: (() -> Unit)? = null
): Disposable {
    val observable = if (isSchedulersIoToMain) this.schedulersIoToMain() else this

    return observable.subscribe(
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
}

fun <T> Flowable<T>.schedulersIoToMain(): Flowable<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun Completable.schedulersIoToMain(): Completable = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

@CheckReturnValue
fun Completable.smartSubscribe(
        isSchedulersIoToMain: Boolean = true,
        onSubscribe: (() -> Unit)? = null,
        onComplete: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onFinally: (() -> Unit)? = null
): Disposable {
    val completable = if (isSchedulersIoToMain) this.schedulersIoToMain() else this

    return completable
            .doOnSubscribe { onSubscribe?.invoke() }
            .subscribe(
                    {
                        onComplete?.invoke()
                        onFinally?.invoke()
                    },
                    { error ->
                        onError?.invoke(error)
                        onFinally?.invoke()
                    }
            )
}

fun <T> Maybe<T>.schedulersIoToMain(): Maybe<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

