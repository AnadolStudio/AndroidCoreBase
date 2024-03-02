package com.anadolstudio.ui.dialogs.bottom_sheet

import androidx.annotation.LayoutRes
import com.anadolstudio.ui.Contentable
import com.anadolstudio.ui.viewmodel.BaseController
import com.anadolstudio.ui.viewmodel.CoreContentViewModel
import com.anadolstudio.ui.viewmodel.observe

abstract class CoreContentBottom<
        ViewState : Any,
        NavigateData : Any,
        ViewModel : CoreContentViewModel<ViewState, NavigateData>,
        Controller : BaseController>(
        @LayoutRes layoutId: Int
) : CoreActionBottom<Controller, NavigateData, ViewModel>(layoutId), Contentable<ViewState, Controller> {

    override fun setupViewModel(viewModel: ViewModel) {
        super.setupViewModel(viewModel)
        observe(viewModel.stateLiveData) { state -> render(state) }
    }

}
