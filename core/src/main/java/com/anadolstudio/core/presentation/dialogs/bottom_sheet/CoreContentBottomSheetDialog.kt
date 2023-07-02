package com.anadolstudio.core.presentation.dialogs.bottom_sheet

import androidx.annotation.LayoutRes
import com.anadolstudio.core.presentation.Contentable
import com.anadolstudio.core.viewmodel.CoreContentViewModel
import com.anadolstudio.core.viewmodel.observe

abstract class CoreContentBottomSheetDialog<ViewState : Any, ViewModel : CoreContentViewModel<ViewState>>(
        @LayoutRes layoutId: Int
) : CoreActionBottomSheetDialog<ViewModel>(layoutId), Contentable<ViewState> {

    override fun setupViewModel() {
        super.setupViewModel()
        observe(viewModel.viewState, this::render)
    }

    override fun showLoading() = Unit

    override fun showEmpty() = Unit

    override fun showError() = Unit

}
