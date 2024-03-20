package com.anadolstudio.ui.viewmodel.states

import com.anadolstudio.utils.util.rx.schedulersIoToMain
import com.anadolstudio.utils.util.rx.smartSubscribe
import io.reactivex.Single
import io.reactivex.disposables.Disposable

sealed class ProgressState(val priority: Int) {

    private companion object {
        const val MAX_PRIORITY = 1
        const val SECOND_PRIORITY = 2
        const val THIRD_PRIORITY = 3
        const val LOW_PRIORITY = 4
    }

    data class Error(val error: Throwable? = null) : ProgressState(priority = MAX_PRIORITY)
    object Loading : ProgressState(priority = SECOND_PRIORITY)
    object LoadingFromError : ProgressState(priority = SECOND_PRIORITY)
    object Content : ProgressState(priority = THIRD_PRIORITY)
    object Empty : ProgressState(priority = THIRD_PRIORITY)
    object Refresh : ProgressState(priority = LOW_PRIORITY)
}

/**
 * Карта перехода состояний (Конечный автомат)
 * @see LoadingDataContext
 *
 * LoadingDataContext.RELOADING (Может быть взыван из любого стейта, но обычно при первой загрузке):
 * В случае успеха переход в состояние CONTENT, в случае ошибки -- в ERROR
 *
 * LoadingDataContext.REFRESH (Может быть взыван из CONTENT или ERROR):
 * В случае успеха переход в состояние CONTENT, в случае ошибки -- в предыдущее состояние (CONTENT или ERROR)
 *
 * LoadingDataContext.RETRY (Может быть взыван только из ERROR):
 * В случае успеха переход в состояние CONTENT, в случае ошибки -- в ERROR
 *
 *
 * @param oldState -- состояние до загрузки, т.е. результата метода initRefreshableState()
 * @param newState -- состояние после окончания загрузки, т.е. результата метода initRefreshableState()
 * Между oldState и newState устанавливается промежуточное состояние из initRefreshableState()
 */
fun LoadingDataContext.toNextState(
    oldState: ProgressState,
    newState: ProgressState
): ProgressState = when (this) {
    LoadingDataContext.INIT_LOADING -> {
        if (newState is ProgressState.Content) newState else ProgressState.Error()
    }

    LoadingDataContext.REFRESH -> {
        if (newState is ProgressState.Content) newState else oldState
    }

    LoadingDataContext.RETRY -> {
        if (newState is ProgressState.Content) newState else ProgressState.Error()
    }
}

fun LoadingDataContext.initRefreshableState(): ProgressState = when (this) {
    LoadingDataContext.INIT_LOADING -> ProgressState.Loading
    LoadingDataContext.REFRESH -> ProgressState.Refresh
    LoadingDataContext.RETRY -> ProgressState.LoadingFromError
}

fun <T> Single<T>.subscribeWithUpdatingState(
    previousState: ProgressState,
    loadingContext: LoadingDataContext,
    onNewState: (ProgressState) -> Unit,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Disposable? = this
    .doOnSubscribe {
        val startProcessState = loadingContext.initRefreshableState()
        onNewState.invoke(startProcessState)
    }
    .run {
        if (loadingContext == LoadingDataContext.RETRY && previousState is ProgressState.Content) {
            null
        } else {
            subscribe(
                {
                    onSuccess.invoke(it)
                    val newState = loadingContext.toNextState(
                        previousState,
                        ProgressState.Content
                    )
                    onNewState.invoke(newState)
                },
                { error ->
                    onError.invoke(error)
                    val newState = loadingContext.toNextState(
                        previousState,
                        ProgressState.Error(error)
                    )
                    onNewState.invoke(newState)
                }
            )
        }
    }

fun <T> Single<T>.smartSubscribeWithUpdatingState(
    previousState: ProgressState,
    loadingContext: LoadingDataContext,
    onNewState: (ProgressState) -> Unit,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Disposable? = this
    .schedulersIoToMain()
    .doOnSubscribe {
        val startProcessState = loadingContext.initRefreshableState()
        onNewState.invoke(startProcessState)
    }
    .run {
        if (loadingContext == LoadingDataContext.RETRY && previousState is ProgressState.Content) {
            null
        } else {
            smartSubscribe(
                isSchedulersIoToMain = false,
                onSuccess = {
                    onSuccess.invoke(it)
                    val newState = loadingContext.toNextState(
                        previousState,
                        ProgressState.Content
                    )
                    onNewState.invoke(newState)
                },
                onError = { error ->
                    onError.invoke(error)
                    val newState = loadingContext.toNextState(
                        previousState,
                        ProgressState.Error(error)
                    )
                    onNewState.invoke(newState)
                }
            )
        }
    }
