package com.anadolstudio.core.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.anadolstudio.core.base.UiEntity
import com.anadolstudio.core.event.SingleErrorSnack
import com.anadolstudio.core.event.SingleErrorToast
import com.anadolstudio.core.event.SingleMessageSnack
import com.anadolstudio.core.event.SingleMessageToast
import com.anadolstudio.core.fragment.Eventable
import com.anadolstudio.core.livedata.SingleCustomEvent
import com.anadolstudio.core.livedata.SingleError
import com.anadolstudio.core.livedata.SingleEvent
import com.anadolstudio.core.livedata.SingleMessage
import com.anadolstudio.core.viewmodel.CoreActionViewModel
import com.anadolstudio.core.viewmodel.observe

abstract class CoreActionActivity<ViewModel : CoreActionViewModel> : AppCompatActivity(),
    UiEntity, Eventable {

    protected lateinit var viewModel: ViewModel

    protected open val eventableDelegate: Eventable get() = Eventable.Delegate(uiEntity = this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupViewModel()
    }

    protected open fun setupViewModel() {
        viewModel = createViewModel()
        observe(viewModel.event) { singleEvent -> handleEvent(singleEvent) }
    }

    protected abstract fun createViewModel(): ViewModel

    override fun provideContext(): Context = this

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
}
