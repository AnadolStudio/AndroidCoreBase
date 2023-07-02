package com.anadolstudio.core.fragment

import com.anadolstudio.core.viewmodel.BaseViewState

interface Contentable<ViewState> {

    fun render(state: BaseViewState<ViewState>)

    fun showContent(content: ViewState)

    fun showLoading()

    fun showStub()

    fun showError()

    class Delegate<ViewState> : Contentable<ViewState> {
        // TODO add manage visibility views in different state
        //  maybe add LoadingStateDelegate

        override fun render(state: BaseViewState<ViewState>) = when (state) {
            is BaseViewState.Content -> showContent(state.content)
            is BaseViewState.Loading -> showLoading()
            is BaseViewState.Stub -> showStub()
            is BaseViewState.Error -> showError()
        }

        override fun showContent(content: ViewState) = Unit

        override fun showLoading() = Unit

        override fun showStub() = Unit

        override fun showError() = Unit
    }
}
