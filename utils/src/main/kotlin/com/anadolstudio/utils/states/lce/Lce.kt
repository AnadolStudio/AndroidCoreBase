package com.anadolstudio.utils.states.lce

import com.anadolstudio.utils.states.lce.Lce.*

/**
 * Wrapper with three states - [Loading], [Content] and [Error].
 * The [Content] state has a content of type [C], and [Error] stores a [Throwable] with an error.
 *
 * In the case when the content is not interesting, but only the state is needed, use [LceState].
 * [LceState] can be obtained by calling [Lce.ignoreContent].
 *
 * @see LceState
 * @see Lce.of
 */
sealed class Lce<out C> : LceState {

    fun contentOrNull(): C? = (this as? Content)?.value
    override fun errorOrNull(): Throwable? = (this as? Error)?.error

    object Loading : Lce<Nothing>(), LceState.Loading {
        override fun toString(): String = "Loading"
    }

    data class Content<C>(val value: C) : Lce<C>(), LceState.Content
    data class Error(override val error: Throwable) : Lce<Nothing>(), LceState.Error

    companion object {
        /**
         * Creates a [Lce] with the state [Content] containing the given [content].
         *
         * Unlike creating [Lce.Content] via the constructor, returns type [Lce].
         * Useful if you need to get a base type.
         */
        fun <C> of(content: C): Lce<C> = Content(content)
    }
}

/** Changes the content of [Lce] if the current state is [Content]. */
inline fun <T, R> Lce<T>.mapContent(map: (T) -> R): Lce<R> {
    return when (this) {
        is Loading -> this
        is Error -> this
        is Content -> Lce.of(map.invoke(value))
    }
}

/** Changes the error if [Lce] is in the [Error] state. */
inline fun <T> Lce<T>.mapError(map: (Throwable) -> Throwable): Lce<T> {
    return if (this is Error) Error(map.invoke(error)) else this
}

/** Returns a different value depending on the state of [Lce]. */
inline fun <T, R> Lce<T>.fold(onLoading: () -> R, onError: (Throwable) -> R, onContent: (T) -> R): R {
    return when (this) {
        is Loading -> onLoading()
        is Error -> onError(error)
        is Content -> onContent(value)
    }
}

/** Changes the [Error] state into [Content] applying [recover] function to [Error.error].
 *  Does not handle exception into [recover] function
 */
inline fun <T> Lce<T>.recover(recover: (Throwable) -> T): Lce<T> {
    return errorOrNull()?.let { Lce.of(recover(it)) } ?: this
}

/** Changes the [Error] state into [Content] applying [recover] function to [Error.error].
 *  If [recover] throws exception then wrap it into [Error]
 */
@Suppress("TooGenericExceptionCaught")
inline fun <T> Lce<T>.recoverCatching(recover: (Throwable) -> T): Lce<T> {
    return errorOrNull()?.let { error ->
        try {
            Content(recover(error))
        } catch (error: Throwable) {
            Error(error)
        }
    } ?: this
}
