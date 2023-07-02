package com.anadolstudio.core.presentation.dialogs.alert

import android.content.Context
import androidx.appcompat.app.AlertDialog

@Deprecated("Need refactor")
object BaseDialogUtil {

    fun buildAlertDialog(
            context: Context,
            buttonsColor: Int,
            positiveButtonText: String = context.getString(android.R.string.ok),
            positiveButtonAction: (() -> Unit)? = null,
            negativeButtonText: String? = null,
            negativeButtonAction: (() -> Unit)? = null,
            title: String? = null,
            message: String? = null
    ): AlertDialog {
        val builder = AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveButtonText) { dialogInterface, _ ->
                    positiveButtonAction?.invoke()
                    dialogInterface.dismiss()
                }

        if (negativeButtonText != null) {
            builder.setNegativeButton(negativeButtonText) { dialogInterface, _ ->
                negativeButtonAction?.invoke()
                dialogInterface.dismiss()
            }
        }

        return builder.show().apply { applyButtonsTextColor(context, buttonsColor) }
    }
}
