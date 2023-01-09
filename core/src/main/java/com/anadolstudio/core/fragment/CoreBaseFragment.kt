package com.anadolstudio.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.anadolstudio.core.event.SingleMessageDialog
import com.anadolstudio.core.event.SingleMessageSnack
import com.anadolstudio.core.event.SingleMessageToast
import com.anadolstudio.core.livedata.SingleCustomEvent
import com.anadolstudio.core.livedata.SingleError
import com.anadolstudio.core.livedata.SingleEvent
import com.anadolstudio.core.livedata.SingleMessage
import com.anadolstudio.core.viewmodel.BaseViewModel
import com.anadolstudio.core.viewmodel.BaseViewState
import com.anadolstudio.core.viewmodel.observe
import com.google.android.material.snackbar.Snackbar

abstract class CoreBaseFragment<ViewState, ViewModel : BaseViewModel<ViewState>>(@LayoutRes private val layoutId: Int) : Fragment() {

    protected lateinit var viewModel: ViewModel

    protected open fun render(state: BaseViewState<ViewState>) = when (state) {
        is BaseViewState.Content -> showContent(state.content)
        is BaseViewState.Loading -> showLoading()
        is BaseViewState.Stub -> showStub()
    }

    protected abstract fun showContent(content: ViewState)
    protected abstract fun showLoading()
    protected open fun showStub() = Unit

    protected open fun setupViewModel() {
        viewModel = createViewModel()
        observe(viewModel.event, this::handleEvent)
        observe(viewModel.state, this::render)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }

    protected abstract fun createViewModel(): ViewModel

    protected open fun handleEvent(event: SingleEvent) = when (event) {
        is SingleMessage -> handleMessageEvent(event)
        is SingleError -> Unit
        is SingleCustomEvent -> Unit
    }

    protected open fun handleMessageEvent(event: SingleMessage) {
        when (event) {
            is SingleMessageToast -> showMessageToast(event)
            is SingleMessageSnack -> showMessageSnackbar(event)
            is SingleMessageDialog -> showMessageDialog(event)
        }
    }

    protected open fun showMessageDialog(event: SingleMessageDialog) {
        when (event) {
            is SingleMessageDialog.Information -> {}
            is SingleMessageDialog.Choice -> {}
        }
        TODO()
    }

    protected open fun showMessageToast(event: SingleMessageToast) {
        val length = when (event) {
            is SingleMessageToast.Long -> Toast.LENGTH_LONG
            is SingleMessageToast.Short -> Toast.LENGTH_SHORT
            else -> return
        }

        Toast.makeText(requireContext(), event.message, length).show()
    }

    protected open fun showMessageSnackbar(event: SingleMessageSnack) {
        val length = when (event) {
            is SingleMessageSnack.Long -> Snackbar.LENGTH_LONG
            is SingleMessageSnack.Short -> Snackbar.LENGTH_SHORT
            is SingleMessageSnack.Indefinite -> Snackbar.LENGTH_INDEFINITE
            else -> return
        }

        onCreateSnackbar(event.message, length)
    }

    protected open fun onCreateSnackbar(message: String, length: Int) = Snackbar.make(requireView(), message, length).show()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
            inflater.inflate(layoutId, container, false)
}
