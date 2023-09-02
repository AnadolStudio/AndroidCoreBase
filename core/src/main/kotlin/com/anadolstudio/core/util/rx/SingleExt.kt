package com.anadolstudio.core.util.rx

import com.anadolstudio.core.viewmodel.LceState
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

fun <T : Any> quickSingleFrom(action: () -> T): Single<T> = singleFrom(action).schedulersIoToMain()

fun <T> Single<T>.schedulersIoToMain(): Single<T> = subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.smartSubscribe(
        isSchedulersIoToMain: Boolean = true,
        onSuccess: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onFinally: (() -> Unit)? = null
): Disposable {
    val single = if (isSchedulersIoToMain) this.schedulersIoToMain() else this

    return single
            .doOnSubscribe {

            }
            .subscribe(
                    { data ->
                        onSuccess?.invoke(data)
                        onFinally?.invoke()
                    },
                    { error ->
                        onError?.invoke(error)
                        onFinally?.invoke()
                    }
            )

}

fun <T> Single<T>.lceSubscribe(
        isSchedulersIoToMain: Boolean = true,
        onLoading: (() -> Unit)? = null,
        onContent: ((T) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        onEach: ((LceState<T>) -> Unit)? = null
): Disposable = (if (isSchedulersIoToMain) this.schedulersIoToMain() else this)
        .doOnSubscribe {
            onEach?.invoke(LceState.Loading())
            onLoading?.invoke()
        }
        .subscribe(
                { data ->
                    onEach?.invoke(LceState.Content(data))
                    onContent?.invoke(data)
                },
                { error ->
                    onEach?.invoke(LceState.Error(error))
                    onError?.invoke(error)
                }
        )
