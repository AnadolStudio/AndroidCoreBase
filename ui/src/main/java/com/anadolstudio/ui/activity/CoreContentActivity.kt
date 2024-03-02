package com.anadolstudio.ui.activity

import com.anadolstudio.ui.Contentable
import com.anadolstudio.ui.viewmodel.BaseController
import com.anadolstudio.ui.viewmodel.CoreContentViewModel
import com.anadolstudio.ui.viewmodel.observe

abstract class CoreContentActivity<
        ViewState : Any,
        NavigateData : Any,
        ViewModel : CoreContentViewModel<ViewState, NavigateData>,
        Controller : BaseController>
    : CoreActionActivity<Controller, NavigateData, ViewModel>(), Contentable<ViewState, Controller> {


    override fun setupViewModel(viewModel: ViewModel) {
        observe(viewModel.stateLiveData) { state -> render(state) }
    }

}
