package com.anadolstudio.core.presentation

import com.anadolstudio.core.presentation.fragment.state_util.ViewStateDelegate
import com.anadolstudio.core.viewmodel.BaseController

interface Contentable<ViewState : ContentableState, Controller : BaseController> {

    val viewStateDelegate: ViewStateDelegate

    fun render(state: ViewState, controller: Controller) = when {
        state.isShimmers -> showShimmers()
        state.isContent -> showContent(state)
        state.isError -> state.getError?.let { showError(it, controller) }
        else -> Unit
    }

    fun showContent(content: ViewState) {
        viewStateDelegate.showContent()

        showFullScreenLoading(content.isFullScreenLoading)
        if (content.isEmpty) showEmpty()
    }

    fun showShimmers() = viewStateDelegate.showLoading()
    fun showFullScreenLoading(isFullScreenLoading: Boolean) = Unit
    fun showEmpty() = viewStateDelegate.showStub()
    fun showError(error: Throwable, controller: Controller) = viewStateDelegate.showError()
}
