package com.anadolstudio.core.dialogs.bottom_sheet

import androidx.annotation.LayoutRes
import com.anadolstudio.core.fragment.Contentable
import com.anadolstudio.core.viewmodel.BaseViewState
import com.anadolstudio.core.viewmodel.CoreContentViewModel
import com.anadolstudio.core.viewmodel.observe

abstract class CoreContentBottomSheetDialog<ViewState, ViewModel : CoreContentViewModel<ViewState>>(
        @LayoutRes layoutId: Int
) : CoreActionBottomSheetDialog<ViewModel>(layoutId), Contentable<ViewState> {

    protected open val contentableDelegate: Contentable<ViewState> get() = Contentable.Delegate()

    override fun setupViewModel() {
        super.setupViewModel()
        observe(viewModel.state, this::render)
    }

    /* Contentable Implementation region*/
    override fun render(state: BaseViewState<ViewState>) = contentableDelegate.render(state)

    override fun showContent(content: ViewState) = contentableDelegate.showContent(content)

    override fun showLoading() = Unit

    override fun showStub() = Unit

    override fun showError() = Unit
    /* Contentable Implementation end region*/

}
