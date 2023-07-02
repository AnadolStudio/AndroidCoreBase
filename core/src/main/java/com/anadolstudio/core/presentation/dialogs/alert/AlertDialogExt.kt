package com.anadolstudio.core.presentation.dialogs.alert

import android.content.Context
import androidx.annotation.ColorRes
import androidx.appcompat.app.AlertDialog

private val alertDialogButtonsId = listOf(
        AlertDialog.BUTTON_POSITIVE,
        AlertDialog.BUTTON_NEGATIVE,
        AlertDialog.BUTTON_NEUTRAL
)

fun AlertDialog.applyButtonsTextColor(
        context: Context,
        @ColorRes colorRes: Int
) = apply {
    alertDialogButtonsId.forEach { buttonId ->
        getButton(buttonId).setTextColor(context.getColor(colorRes))
    }
}

fun AlertDialog.applyButtonsTextBoldStyle() = apply {
    alertDialogButtonsId.forEach { buttonId ->
        getButton(buttonId).setTypeface(android.graphics.Typeface.DEFAULT, android.graphics.Typeface.BOLD)
    }
}
