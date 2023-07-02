package com.anadolstudio.core.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.anadolstudio.core.presentation.Contentable
import com.anadolstudio.core.viewmodel.CoreContentViewModel
import com.anadolstudio.core.viewmodel.observe

abstract class CoreContentBaseFragment<ViewState : Any, ViewModel : CoreContentViewModel<ViewState>>(
        @LayoutRes layoutId: Int
) : CoreActionBaseFragment<ViewModel>(layoutId), Contentable<ViewState> {

    override fun setupViewModel() {
        super.setupViewModel()
        observe(viewModel.viewState, this::render)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    override fun showLoading() = Unit

    override fun showEmpty() = Unit

    override fun showError() = Unit

}
