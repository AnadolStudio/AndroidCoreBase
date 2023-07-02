package com.anadolstudio.core.presentation.fragment.state_util

import android.view.View

class LoadingStateDelegate : StateDelegate<LoadingStateDelegate.LoadingState> {

    companion object {

        private fun convertToList(view: View?): List<View> {
            return if (view != null) listOf(view) else emptyList()
        }
    }

    constructor(contentView: View? = null, loadingView: View? = null, stubView: View? = null) : super(
            State(LoadingState.CONTENT, convertToList(contentView)),
            State(LoadingState.LOADING, convertToList(loadingView)),
            State(LoadingState.STUB, convertToList(stubView))
    )

    constructor(contentView: List<View>, loadingView: List<View>, stubView: List<View>) : super(
            State(LoadingState.CONTENT, contentView),
            State(LoadingState.LOADING, loadingView),
            State(LoadingState.STUB, stubView)
    )

    fun showContent() {
        this.currentState = LoadingState.CONTENT
    }

    fun showLoading() {
        this.currentState = LoadingState.LOADING
    }

    fun showStub() {
        this.currentState = LoadingState.STUB
    }

    enum class LoadingState {
        LOADING,
        CONTENT,
        STUB
    }
}
