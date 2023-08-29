package com.anadolstudio.core.presentation.activity

import com.anadolstudio.core.presentation.Contentable
import com.anadolstudio.core.viewmodel.BaseController
import com.anadolstudio.core.viewmodel.CoreContentViewModel
import com.anadolstudio.core.viewmodel.observe

abstract class CoreContentActivity<
        ViewState : Any,
        NavigateData : Any,
        ViewModel : CoreContentViewModel<ViewState, NavigateData>,
        Controller : BaseController>
    : CoreActionActivity<Controller, NavigateData, ViewModel>(), Contentable<ViewState, Controller> {


    override fun setupViewModel(viewModel: ViewModel) {
        observe(viewModel.stateLiveData) { state -> render(state, controller) }
    }

}
