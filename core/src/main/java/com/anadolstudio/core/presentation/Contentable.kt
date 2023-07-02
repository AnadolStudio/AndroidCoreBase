package com.anadolstudio.core.presentation

import com.anadolstudio.core.viewmodel.Lce
import com.anadolstudio.core.viewmodel.Lce.Content.Data
import com.anadolstudio.core.viewmodel.Lce.Content.Empty

interface Contentable<ViewState> {

    fun render(state: Lce<ViewState>) = when (state) {
        is Lce.Content -> showContent(state)
        is Lce.Loading -> showLoading()
        is Lce.Error -> showError()
    }

    fun showContent(content: Lce.Content<ViewState>) = when (content) {
        is Data -> showData(content.data)
        is Empty -> showEmpty()
    }

    fun showData(content: ViewState)

    fun showLoading()

    fun showEmpty()

    fun showError()

}
