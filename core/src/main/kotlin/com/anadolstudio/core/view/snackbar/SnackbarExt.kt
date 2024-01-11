package com.anadolstudio.core.view.snackbar

import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat

import com.google.android.material.snackbar.Snackbar

fun Snackbar.decorate(@ColorRes backgroundId: Int, @ColorRes textColorId: Int, textAppearanceId: Int): Snackbar {
    val layout = view as Snackbar.SnackbarLayout

    val textView = with(layout) {
        setBackgroundColor(ContextCompat.getColor(view.context, backgroundId))
        findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
    }

    with(textView) {
        setTextColor(ContextCompat.getColor(view.context, textColorId))
        TextViewCompat.setTextAppearance(this, textAppearanceId)
        maxLines = Int.MAX_VALUE
        ellipsize = null
    }

    with(layout.findViewById(com.google.android.material.R.id.snackbar_action) as TextView) {
        setTextColor(ContextCompat.getColor(view.context, textColorId))
        TextViewCompat.setTextAppearance(this, textAppearanceId)
    }

    return this
}
