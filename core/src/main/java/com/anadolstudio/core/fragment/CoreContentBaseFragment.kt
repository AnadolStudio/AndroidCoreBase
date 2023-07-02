package com.anadolstudio.core.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.anadolstudio.core.viewmodel.CoreContentViewModel
import com.anadolstudio.core.viewmodel.BaseViewState
import com.anadolstudio.core.viewmodel.observe

abstract class CoreContentBaseFragment<ViewState, ViewModel : CoreContentViewModel<ViewState>>(
        @LayoutRes layoutId: Int
) : CoreActionBaseFragment<ViewModel>(layoutId), Contentable<ViewState> {

    protected open val contentableDelegate: Contentable<ViewState> get() = Contentable.Delegate()

    override fun setupViewModel() {
        super.setupViewModel()
        observe(viewModel.state, this::render)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    /* Contentable Implementation region*/
    override fun render(state: BaseViewState<ViewState>) = contentableDelegate.render(state)

    override fun showContent(content: ViewState) = contentableDelegate.showContent(content)

    override fun showLoading() = Unit

    override fun showStub() = Unit

    override fun showError() = Unit
    /* Contentable Implementation end region*/

}
