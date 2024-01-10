package com.anadolstudio.core.presentation.dialogs.alert

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.anadolstudio.core.R

object AlertDialogBuilder {

    fun buildChoiceDialog(
            context: Context,
            title: String? = null,
            message: String? = null,
            @StringRes positiveTextRes: Int = R.string.yes,
            @StringRes negativeTextRes: Int = android.R.string.cancel,
            onPositiveButtonClicked: () -> Unit = {},
            onNegativeButtonClicked: (() -> Unit) = {},
            cancelable: Boolean = false
    ): AlertDialog = buildDialog(
            context = context,
            title = title,
            message = message,
            positiveTextRes = positiveTextRes,
            negativeTextRes = negativeTextRes,
            onPositiveButtonClicked = onPositiveButtonClicked,
            onNegativeButtonClicked = onNegativeButtonClicked,
            cancelable = cancelable
    )

    fun buildInformationDialog(
            context: Context,
            title: String? = null,
            message: String? = null,
            @StringRes positiveTextRes: Int = R.string.yes,
            onPositiveButtonClicked: () -> Unit = {},
    ): AlertDialog = buildDialog(
            context = context,
            title = title,
            message = message,
            positiveTextRes = positiveTextRes,
            onPositiveButtonClicked = onPositiveButtonClicked
    )

    fun buildDialog(
            context: Context,
            title: String? = null,
            message: String? = null,
            @StringRes positiveTextRes: Int = R.string.yes,
            @StringRes negativeTextRes: Int? = null,
            onPositiveButtonClicked: () -> Unit = {},
            onNegativeButtonClicked: (() -> Unit)? = null,
            cancelable: Boolean = true
    ): AlertDialog = AlertDialog.Builder(context, R.style.DialogAlertTheme)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(cancelable)
            .run {
                negativeTextRes
                        ?.let {
                            setNegativeButton(negativeTextRes) { dialog, _ ->
                                dialog.dismiss()
                                onNegativeButtonClicked?.invoke()
                            }
                        }
                        ?: this
            }
            .setPositiveButton(positiveTextRes) { dialog, _ ->
                dialog.dismiss()
                onPositiveButtonClicked.invoke()
            }
            .show()
            .applyButtonsTextColor(context, R.color.colorAccent)

}
