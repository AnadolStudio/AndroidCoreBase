package com.anadolstudio.utils.states.lce

/**
 * Turns [Lce] into [Result] for cases where the loading state is not needed and the final result is important.
 * If [Lce] is in the [Lce.Loading] state, it will return `null`.
 */
internal fun <T> Lce<T>.toResultOrNull(): Result<T>? {
    return fold(
        onLoading = { null },
        onError = { Result.failure(it) },
        onContent = { content -> Result.success(content) },
    )
}

/**
 * Turns [LceState] into [Result] for cases where the loading state is not needed and the final result is important.
 * If [LceState] is in the [LceState.Loading] state, it will return `null`.
 */
internal fun LceState.toResultOrNull(): Result<Unit>? {
    return fold(
        onLoading = { null },
        onError = { Result.failure(it) },
        onContent = { Result.success(Unit) },
    )
}

/** Turns [Result] into [LceState]. */
internal fun Result<*>.toLceState(): LceState {
    return fold(
        onSuccess = { LceState.Content },
        onFailure = { LceState.Error(it) },
    )
}
