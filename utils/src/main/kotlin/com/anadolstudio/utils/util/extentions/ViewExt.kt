package com.anadolstudio.utils.util.extentions

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.anadolstudio.utils.R
import com.anadolstudio.utils.util.common.dpToPx
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory

val View.centerX: Int get() = width / 2
val View.centerY: Int get() = height / 2

var View.scale: Float
    set(value) {
        scaleX = value
        scaleY = value
    }
    get() = scaleX

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

fun View.setMargins(start: Int? = null, top: Int? = null, end: Int? = null, bottom: Int? = null) {
    setDimensMargins(
            start = start?.dpToPx(),
            top = top?.dpToPx(),
            end = end?.dpToPx(),
            bottom = bottom?.dpToPx(),
    )
}

fun View.setDimensMargins(start: Int? = null, top: Int? = null, end: Int? = null, bottom: Int? = null) {
    val params = getMarginLayoutParams()
    params?.setMargins(
            start ?: params.marginStart,
            top ?: params.topMargin,
            end ?: params.marginEnd,
            bottom ?: params.bottomMargin
    )
}

fun View.getMarginLayoutParams(): ViewGroup.MarginLayoutParams? = layoutParams as? ViewGroup.MarginLayoutParams

fun ImageView.setImageFromUrl(
    url: String?,
    @DrawableRes errorId: Int? = null,
    @DrawableRes placeholderId: Int? = null,
) {
    Glide
        .with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply {
            errorId?.let(this::error)
            placeholderId?.let(this::placeholder)
        }
        .transition(
            DrawableTransitionOptions.withCrossFade()
                // Для фикса бага с наложением плейсхолдера и изображения
                // Подробности: http://bumptech.github.io/glide/doc/transitions.html#cross-fading-with-placeholders-and-transparent-images
                .crossFade(
                    DrawableCrossFadeFactory
                        .Builder()
                        .setCrossFadeEnabled(true)
                        .build()
                )
        )
        .into(this)
}
