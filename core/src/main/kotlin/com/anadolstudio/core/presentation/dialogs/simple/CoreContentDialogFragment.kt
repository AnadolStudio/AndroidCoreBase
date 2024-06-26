package com.anadolstudio.core.presentation.dialogs.simple

import androidx.annotation.LayoutRes
import com.anadolstudio.core.presentation.Contentable
import com.anadolstudio.viewmodel.BaseController
import com.anadolstudio.viewmodel.CoreContentViewModel
import com.anadolstudio.viewmodel.observe

abstract class CoreContentDialogFragment<
        ViewState : Any,
        NavigateData : Any,
        ViewModel : CoreContentViewModel<ViewState, NavigateData>,
        Controller : BaseController>(
        @LayoutRes layoutId: Int
) : CoreActionDialogFragment<Controller, NavigateData,ViewModel>(layoutId), Contentable<ViewState, Controller> {

    override fun setupViewModel(viewModel: ViewModel) {
        super.setupViewModel(viewModel)
        observe(viewModel.stateLiveData) { state -> render(state) }
    }

}
