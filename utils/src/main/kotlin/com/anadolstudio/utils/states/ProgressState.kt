package com.anadolstudio.utils.states

import io.reactivex.Single
import io.reactivex.disposables.Disposable

sealed class ProgressState {

    data class Error(val error: Throwable? = null) : ProgressState()
    object Loading : ProgressState()
    object LoadingFromError : ProgressState()
    object Content : ProgressState()
    object Refresh : ProgressState()
}

/**
 * Карта перехода состояний (Конечный автомат)
 * @see LoadingContext
 *
 * LoadingDataContext.INIT_LOADING (Может быть взыван из любого стейта, но обычно при первой загрузке):
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
fun LoadingContext.toEndState(
    oldState: ProgressState,
    newState: ProgressState
): ProgressState = when (this) {
    LoadingContext.INIT_LOADING, LoadingContext.RETRY -> {
        if (newState is ProgressState.Content || newState is ProgressState.Error) newState else ProgressState.Error()
    }

    LoadingContext.REFRESH -> {
        if (newState is ProgressState.Content) newState else oldState
    }
}

fun LoadingContext.toContent(oldState: ProgressState): ProgressState = toEndState(oldState, ProgressState.Content)

fun LoadingContext.toError(oldState: ProgressState, error: Throwable): ProgressState =
    toEndState(oldState, ProgressState.Error(error))

fun LoadingContext.toStartState(): ProgressState = when (this) {
    LoadingContext.INIT_LOADING -> ProgressState.Loading
    LoadingContext.REFRESH -> ProgressState.Refresh
    LoadingContext.RETRY -> ProgressState.LoadingFromError
}

fun <T> Single<T>.subscribeWithUpdatingState(
    previousState: ProgressState,
    loadingContext: LoadingContext,
    onNewState: (ProgressState) -> Unit,
    onSuccess: (T) -> Unit,
    onError: (Throwable) -> Unit
): Disposable? = this
    .doOnSubscribe { onNewState.invoke(loadingContext.toStartState()) }
    .run {
        if (loadingContext == LoadingContext.RETRY && previousState is ProgressState.Content) {
            null
        } else {
            subscribe(
                {
                    onSuccess.invoke(it)
                    onNewState.invoke(loadingContext.toContent(previousState))
                },
                { error ->
                    onError.invoke(error)
                    onNewState.invoke(loadingContext.toError(previousState, error))
                }
            )
        }
    }
