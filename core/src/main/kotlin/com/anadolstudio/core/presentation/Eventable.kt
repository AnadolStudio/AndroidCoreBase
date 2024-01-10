package com.anadolstudio.core.presentation

import android.content.Context
import android.view.View
import android.widget.Toast
import com.anadolstudio.core.presentation.event.SingleErrorSnack
import com.anadolstudio.core.presentation.event.SingleErrorToast
import com.anadolstudio.core.presentation.event.SingleMessageSnack
import com.anadolstudio.core.presentation.event.SingleMessageToast
import com.anadolstudio.core.viewmodel.livedata.SingleCustomEvent
import com.anadolstudio.core.viewmodel.livedata.SingleError
import com.anadolstudio.core.viewmodel.livedata.SingleEvent
import com.anadolstudio.core.viewmodel.livedata.SingleMessage
import com.google.android.material.snackbar.Snackbar

interface Eventable {

    fun handleEvent(event: SingleEvent)

    fun handleMessageEvent(event: SingleMessage)

    fun showMessageToast(event: SingleMessageToast)

    fun showMessageSnackbar(event: SingleMessageSnack)

    fun handleErrorEvent(event: SingleError)

    fun showErrorToast(event: SingleErrorToast)

    fun showErrorSnackbar(event: SingleErrorSnack)

    fun handleCustomEvent(event: SingleCustomEvent)

    fun createSnackbar(rootView: View, message: String, length: Int)

    fun createToast(context: Context, message: String, length: Int)

    class Delegate<Ui : UiEntity>(protected val uiEntity: Ui) : Eventable {

        override fun handleEvent(event: SingleEvent) = when (event) {
            is SingleMessage -> handleMessageEvent(event)
            is SingleError -> handleErrorEvent(event)
            is SingleCustomEvent -> handleCustomEvent(event)
        }

        override fun handleMessageEvent(event: SingleMessage) = when (event) {
            is SingleMessageToast -> showMessageToast(event)
            is SingleMessageSnack -> showMessageSnackbar(event)
            else -> Unit
        }

        override fun showMessageToast(event: SingleMessageToast) {
            val length = when (event) {
                is SingleMessageToast.Long -> Toast.LENGTH_LONG
                is SingleMessageToast.Short -> Toast.LENGTH_SHORT
            }

            createToast(uiEntity.provideContext(), event.message, length)
        }

        override fun showMessageSnackbar(event: SingleMessageSnack) {
            val length = when (event) {
                is SingleMessageSnack.Long -> Snackbar.LENGTH_LONG
                is SingleMessageSnack.Short -> Snackbar.LENGTH_SHORT
                is SingleMessageSnack.Indefinite -> Snackbar.LENGTH_INDEFINITE
            }

            createSnackbar(uiEntity.provideRootView(), event.message, length)
        }

        override fun handleErrorEvent(event: SingleError) {
            when (event) {
                is SingleErrorToast -> showErrorToast(event)
                is SingleErrorSnack -> showErrorSnackbar(event)
            }
        }

        override fun showErrorToast(event: SingleErrorToast) {
            val length = when (event) {
                is SingleErrorToast.Long -> Toast.LENGTH_LONG
                is SingleErrorToast.Short -> Toast.LENGTH_SHORT
            }

            createToast(uiEntity.provideContext(), event.error.message.orEmpty(), length)
        }

        override fun showErrorSnackbar(event: SingleErrorSnack) {
            val length = when (event) {
                is SingleErrorSnack.Long -> Snackbar.LENGTH_LONG
                is SingleErrorSnack.Short -> Snackbar.LENGTH_SHORT
            }

            createSnackbar(uiEntity.provideRootView(), event.error.message.orEmpty(), length)
        }

        override fun handleCustomEvent(event: SingleCustomEvent) = Unit

        override fun createSnackbar(rootView: View, message: String, length: Int) {
            Snackbar.make(rootView, message, length).show()
        }

        override fun createToast(context: Context, message: String, length: Int) {
            Toast.makeText(context, message, length).show()
        }
    }
}
