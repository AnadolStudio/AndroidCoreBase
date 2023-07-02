package com.anadolstudio.core.presentation.activity

import com.anadolstudio.core.presentation.Contentable
import com.anadolstudio.core.viewmodel.CoreContentViewModel
import com.anadolstudio.core.viewmodel.observe

abstract class CoreContentActivity<ViewState : Any, ViewModel : CoreContentViewModel<ViewState>>
    : CoreActionActivity<ViewModel>(), Contentable<ViewState> {

    override fun setupViewModel() {
        super.setupViewModel()
        observe(viewModel.viewState, this::render)
    }

    override fun showLoading() = Unit

    override fun showEmpty() = Unit

    override fun showError() = Unit
}
