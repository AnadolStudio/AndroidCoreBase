package com.anadolstudio.core.presentation

interface ContentableState {
    val isShimmers: Boolean
    val isContent: Boolean
    val isFullScreenLoading: Boolean
    val isEmpty: Boolean
    val isError: Boolean

    val getError: Throwable?
}
