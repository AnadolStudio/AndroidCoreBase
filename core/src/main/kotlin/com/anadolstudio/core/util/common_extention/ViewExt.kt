package com.anadolstudio.core.util.common_extention

import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import com.anadolstudio.core.R

fun View.makeGone() {
    this.visibility = View.GONE
}

fun View.makeVisible() {
    this.visibility = View.VISIBLE
}

fun View.makeInvisible() {
    this.visibility = View.INVISIBLE
}

fun View.setSmartPadding(
        start: Int? = null,
        top: Int? = null,
        end: Int? = null,
        bottom: Int? = null,
) {
    setPadding(
            start ?: paddingStart,
            top ?: paddingTop,
            end ?: paddingEnd,
            bottom ?: paddingBottom,
    )
}

fun View.enable() {
    isEnabled = true
}

fun View.disable() {
    isEnabled = false
}

fun TextView.setTextOrMakeGone(text: CharSequence?) {
    this.text = text
    isVisible = text != null
}

fun TextView.setTextOrMakeGoneIfBlank(text: CharSequence?) {
    this.text = text
    isVisible = !text.isNullOrBlank()
}

fun TextView.setLimitText(text: CharSequence?, limit: Int) {
    val correctName = if (text == null || text.length <= limit) {
        text
    } else {
        text.take(limit - 1).trim().toString().plus(context.getString(R.string.symbol_multi_dot))
    }

    this.text = correctName
}
