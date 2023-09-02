package com.anadolstudio.core.presentation.dialogs.bottom_sheet

import androidx.annotation.LayoutRes
import com.anadolstudio.core.presentation.Contentable
import com.anadolstudio.core.viewmodel.BaseController
import com.anadolstudio.core.viewmodel.CoreContentViewModel
import com.anadolstudio.core.viewmodel.observe

abstract class CoreContentBottomSheetDialog<
        ViewState : Any,
        NavigateData : Any,
        ViewModel : CoreContentViewModel<ViewState, NavigateData>,
        Controller : BaseController>(
        @LayoutRes layoutId: Int
) : CoreActionBottomSheetDialog<Controller, NavigateData,ViewModel>(layoutId), Contentable<ViewState, Controller> {

    override fun setupViewModel(viewModel: ViewModel) {
        super.setupViewModel(viewModel)
        observe(viewModel.stateLiveData) { state -> render(state, controller) }
    }

}
