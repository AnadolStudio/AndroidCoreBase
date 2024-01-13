package com.anadolstudio.core.presentation.dialogs.alert

import android.content.Context
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AlertDialog
import com.anadolstudio.core.R

object AlertDialogBuilder {

    fun buildChoiceDialog(
            context: Context,
            @StyleRes theme: Int = R.style.DialogAlertTheme,
            title: String? = null,
            message: String? = null,
            @StringRes positiveTextRes: Int = R.string.yes,
            @StringRes negativeTextRes: Int = R.string.no,
            onPositiveButtonClicked: () -> Unit = {},
            onNegativeButtonClicked: (() -> Unit) = {},
            cancelable: Boolean = false
    ): AlertDialog = buildDialog(
            context = context,
            theme = theme,
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
            @StyleRes theme: Int = R.style.DialogAlertTheme,
            title: String? = null,
            message: String? = null,
            @StringRes positiveTextRes: Int = R.string.yes,
            onPositiveButtonClicked: () -> Unit = {},
    ): AlertDialog = buildDialog(
            context = context,
            theme = theme,
            title = title,
            message = message,
            positiveTextRes = positiveTextRes,
            onPositiveButtonClicked = onPositiveButtonClicked
    )

    fun buildDialog(
            context: Context,
            @StyleRes theme: Int = R.style.DialogAlertTheme,
            title: String? = null,
            message: String? = null,
            @StringRes positiveTextRes: Int = R.string.yes,
            @StringRes negativeTextRes: Int? = null,
            onPositiveButtonClicked: () -> Unit = {},
            onNegativeButtonClicked: (() -> Unit)? = null,
            cancelable: Boolean = true
    ): AlertDialog = AlertDialog.Builder(context, theme)
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
