@file:Suppress("TooManyFunctions")

package com.anadolstudio.utils.states.lce

import com.anadolstudio.utils.states.LoadingContext
import com.anadolstudio.utils.states.ProgressState
import com.anadolstudio.utils.states.toStartState
import com.anadolstudio.utils.states.toContent
import com.anadolstudio.utils.states.toError
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.transformLatest

typealias LceFlow<T> = Flow<Lce<T>>
typealias LceStateFlow = Flow<LceState>

inline fun <T> lceFlow(crossinline block: suspend FlowCollector<T>.() -> Unit): LceFlow<T> {
    return flow { block() }.mapToLce()
}

/**
 * When you create a [LceStateFlow] with this function, you don't need to call
 * `emit(Unit)` at the end of the [block].
 */
inline fun lceStateFlow(crossinline block: suspend FlowCollector<Unit>.() -> Unit): LceStateFlow {
    return flow {
        block()
        emit(Unit)
    }.mapToLce()
}

fun <T> Flow<T>.mapToLce(): LceFlow<T> {
    return map<T, Lce<T>> { Lce.Content(it) }
        .onStart { emit(Lce.Loading) }
        .catch { throwable ->
            emit(Lce.Error(throwable))
        }
}

@JvmName("mapToLceUnit")
fun Flow<Unit>.mapToLce(): LceStateFlow {
    return map<Unit, LceState> { Lce.Content(Unit) }
        .onStart { emit(Lce.Loading) }
        .catch { throwable ->
            emit(Lce.Error(throwable))
        }
}

inline fun <T, R> LceFlow<T>.mapLceContent(crossinline transform: suspend (T) -> R): LceFlow<R> {
    return map { lce -> lce.mapContent { transform(it) } }
}

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <T, R> LceFlow<T>.flatMapLceContent(crossinline transform: suspend (T) -> LceFlow<R>): LceFlow<R> {
    return transformLatest { lce ->
        when (lce) {
            is Lce.Content -> emitAll(transform(lce.value))
            is Lce.Error -> emit(lce)
            is Lce.Loading -> emit(lce)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
inline fun LceStateFlow.flatMapContentState(crossinline transform: suspend () -> LceStateFlow): LceStateFlow {
    return transformLatest { lce ->
        when (lce) {
            is LceState.Content -> emitAll(transform())
            is Lce.Error -> emit(lce)
            is Lce.Loading -> emit(lce)
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
inline fun <T, R> LceFlow<T>.flatMapContent(crossinline transform: suspend (T) -> LceFlow<R>): LceFlow<R> {
    return transformLatest { lce ->
        when (lce) {
            is Lce.Content -> emitAll(transform(lce.value))
            is Lce.Error -> emit(lce)
            is Lce.Loading -> emit(lce)
        }
    }
}

inline fun <T, R> LceFlow<T>.foldEach(
    crossinline onLoading: suspend () -> R,
    crossinline onError: suspend (Throwable) -> R,
    crossinline onContent: suspend (T) -> R,
): Flow<R> {
    return map { lceState ->
        lceState.fold(
            onLoading = { onLoading.invoke() },
            onError = { onError.invoke(it) },
            onContent = { content: T -> onContent.invoke(content) }
        )
    }
}

inline fun <T : LceState, R> Flow<T>.foldEach(
    crossinline onLoading: suspend () -> R,
    crossinline onError: suspend (Throwable) -> R,
    crossinline onContent: suspend () -> R,
): Flow<R> {
    return map { lceState ->
        lceState.fold(
            onLoading = { onLoading.invoke() },
            onError = { onError.invoke(it) },
            onContent = { onContent.invoke() }
        )
    }
}

fun <T> LceFlow<T>.ignoreContent(): Flow<LceState> = map { it.ignoreContent() }

fun <T> LceFlow<T>.ignoreLoading(): Flow<Result<T>> = mapNotNull { it.toResultOrNull() }

@JvmName("ignoreLoadingUnit")
fun LceStateFlow.ignoreLoading(): Flow<Result<Unit>> = mapNotNull { it.toResultOrNull() }

suspend inline fun <T> LceFlow<T>.contentOrNull(): T? {
    return filterIsInstance<Lce.Content<T>>().firstOrNull()?.value
}

inline fun <T> LceFlow<T>.recover(crossinline recover: suspend (Throwable) -> T): LceFlow<T> {
    return map { lce -> lce.recover { recover(it) } }
}

inline fun <T> LceFlow<T>.recoverCatching(crossinline recover: suspend (Throwable) -> T): LceFlow<T> {
    return map { lce -> lce.recoverCatching { recover(it) } }
}

inline fun <T> LceFlow<T>.mapLceError(crossinline recover: suspend (Throwable) -> Lce<T>): LceFlow<T> {
    return transform { lce ->
        if (lce is Lce.Error) emit(recover(lce.error)) else emit(lce)
    }
}

private inline fun <T : LceState, reified R> Flow<T>.onEachLceType(crossinline onType: suspend (R) -> Unit): Flow<T> {
    return onEach { lce -> if (lce is R) onType(lce) }
}

fun <T : LceState> Flow<T>.onEachLoading(onLoading: suspend () -> Unit): Flow<T> {
    return onEachLceType<T, LceState.Loading> { onLoading() }
}

fun <T : LceState> Flow<T>.onEachError(onError: suspend (Throwable) -> Unit): Flow<T> {
    return onEachLceType<T, LceState.Error> { onError(it.error) }
}

fun LceStateFlow.onEachContent(onContent: suspend () -> Unit): Flow<LceState> {
    return onEachLceType<LceState, LceState.Content> { onContent() }
}

fun <T> LceFlow<T>.onEachContent(onContent: suspend (T) -> Unit): LceFlow<T> {
    return onEachLceType<Lce<T>, Lce.Content<T>> { onContent(it.value) }
}

fun <T> LceFlow<T>.onEachProgressState(
    previousState: ProgressState,
    loadingContext: LoadingContext,
    onNewProgressState: (ProgressState) -> Unit
): LceFlow<T> = onEach {
        when (it) {
            Lce.Loading -> {
                onNewProgressState.invoke(loadingContext.toStartState())
            }
            is Lce.Content -> {
                onNewProgressState.invoke(loadingContext.toContent(previousState))
            }
            is Lce.Error -> {
                onNewProgressState.invoke(loadingContext.toError(previousState, it.error))
            }
        }
    }

