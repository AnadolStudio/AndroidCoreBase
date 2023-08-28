package com.anadolstudio.core.presentation.fragment.state_util

import android.view.View
import com.anadolstudio.core.presentation.fragment.state_util.state.AllHideInitialState
import com.anadolstudio.core.presentation.fragment.state_util.state.InitialState
import com.anadolstudio.core.presentation.fragment.state_util.state.State
import com.anadolstudio.core.presentation.fragment.state_util.state.StateDelegate

class ViewStateDelegate : StateDelegate<ViewStateDelegate.LoadingState> {

    companion object {

        private fun convertToList(view: View?): List<View> {
            return if (view != null) listOf(view) else emptyList()
        }
    }

    constructor(
            contentView: View? = null,
            loadingView: View? = null,
            stubView: View? = null,
            errorView: View? = null,
            initialState: InitialState<LoadingState> = AllHideInitialState()
    ) : super(
            State(LoadingState.CONTENT, convertToList(contentView)),
            State(LoadingState.LOADING, convertToList(loadingView)),
            State(LoadingState.STUB, convertToList(stubView)),
            State(LoadingState.ERROR, convertToList(errorView)),
            initialState = initialState
    )

    constructor(
            contentViews: List<View>,
            loadingViews: List<View>,
            stubViews: List<View>,
            errorViews: List<View>,
            initialState: InitialState<LoadingState> = AllHideInitialState()
    ) : super(
            State(LoadingState.CONTENT, contentViews),
            State(LoadingState.LOADING, loadingViews),
            State(LoadingState.STUB, stubViews),
            State(LoadingState.ERROR, errorViews),
            initialState = initialState
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

    fun showError() {
        this.currentState = LoadingState.ERROR
    }

    enum class LoadingState {
        LOADING,
        CONTENT,
        STUB,
        ERROR
    }
}
