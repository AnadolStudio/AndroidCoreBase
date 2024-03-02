package com.anadolstudio.ui.dialogs.bottom_sheet

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import com.anadolstudio.ui.viewmodel.livedata.SingleCustomEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleError
import com.anadolstudio.ui.viewmodel.livedata.SingleEvent
import com.anadolstudio.ui.viewmodel.livedata.SingleMessage
import com.anadolstudio.ui.navigation.NavigationEvent
import com.anadolstudio.ui.Eventable
import com.anadolstudio.ui.Navigatable
import com.anadolstudio.ui.UiEntity
import com.anadolstudio.ui.SingleErrorSnack
import com.anadolstudio.ui.SingleErrorToast
import com.anadolstudio.ui.SingleMessageSnack
import com.anadolstudio.ui.SingleMessageToast
import com.anadolstudio.ui.viewmodel.BaseController
import com.anadolstudio.ui.viewmodel.CoreActionViewModel
import com.anadolstudio.ui.viewmodel.observe

abstract class CoreActionBottom<
        Controller : BaseController,
        NavigateData : Any,
        ViewModel : CoreActionViewModel<NavigateData>>(
        @LayoutRes layoutId: Int
) : CoreBottom(layoutId), Eventable, UiEntity, Navigatable<NavigateData> {

    private val viewModel: ViewModel by lazy { createViewModel() }
    protected val controller: Controller get() = viewModel as Controller
    protected open val eventableDelegate: Eventable get() = Eventable.Delegate(uiEntity = this)
    abstract val navigatableDelegate: Navigatable<NavigateData>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel(viewModel)
        initView()
    }

    abstract fun initView()

    override fun getDialogTag(): String = this::class.simpleName.toString()

    protected open fun setupViewModel(viewModel: ViewModel) {
        observe(viewModel.event) { singleEvent -> handleEvent(singleEvent) }
        observe(viewModel.navigation) { navigationEvent -> handleNavigationEvent(navigationEvent) }
    }

    protected abstract fun createViewModel(): ViewModel

    override fun provideContext(): Context = requireContext()

    override fun provideRootView(): View = requireDialog().findViewById(android.R.id.content)

    /* Eventable Implementation region*/
    override fun handleEvent(event: SingleEvent) = eventableDelegate.handleEvent(event)

    override fun handleMessageEvent(event: SingleMessage) = eventableDelegate.handleMessageEvent(event)

    override fun showMessageToast(event: SingleMessageToast) = eventableDelegate.showMessageToast(event)

    override fun showMessageSnackbar(event: SingleMessageSnack) = eventableDelegate.showMessageSnackbar(event)

    override fun handleErrorEvent(event: SingleError) = eventableDelegate.handleErrorEvent(event)

    override fun showErrorToast(event: SingleErrorToast) = eventableDelegate.showErrorToast(event)

    override fun showErrorSnackbar(event: SingleErrorSnack) = eventableDelegate.showErrorSnackbar(event)

    override fun handleCustomEvent(event: SingleCustomEvent) = eventableDelegate.handleCustomEvent(event)

    override fun createSnackbar(rootView: View, message: String, length: Int) = eventableDelegate.createSnackbar(rootView, message, length)

    override fun createToast(context: Context, message: String, length: Int) = eventableDelegate.createToast(context, message, length)
    /* Eventable Implementation end region*/

    /* Navigatable Implementation region*/
    override fun handleNavigationEvent(event: NavigationEvent<NavigateData>) = navigatableDelegate.handleNavigationEvent(event)
    /* Navigatable Implementation end region*/
}
