package com.anadolstudio.core.view.basetextinput.delegates.support_hint

import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes

@Suppress("detekt.TooManyFunctions")
interface InputSupportHintDelegate {

    companion object {
        // TODO Заменить на использование цветов из ресурсов
        const val DEFAULT_ERROR_COLOR = Color.RED
        const val DEFAULT_HINT_COLOR = Color.LTGRAY
    }

    fun showSupportHint()
    fun hideSupportHint()
    fun supportHintIsShow(): Boolean
    fun isFloating(isFloating: Boolean)
    fun setSupportHintEnable(isEnable: Boolean)
    fun setSupportHintMode(mode: ShowSupportHintMode)
    fun getSupportHintState(): SupportHintState
    fun hasError(): Boolean

    fun setSupportHintText(text: String?)
    fun setSupportHintText(text: String?, state: SupportHintState)
    fun setSupportHintText(@StringRes textId: Int)
    fun setSupportHintTextColor(color: Int)
    fun setSupportHintTextColorId(@ColorRes colorId: Int)
    fun setSupportHintStartDrawable(@DrawableRes drawableId: Int)
    fun setSupportHintStartDrawable(drawable: Drawable?)
    fun setSupportHintStartDrawable(drawable: Drawable?, sizePx: Int?, gravityInt: Int?, marginPx: Int?)
    fun clearSupportHintStartDrawable()
    fun getSupportHintText(): String?

    fun showInformationHint()
    fun showInformationHint(text: String?)
    fun setInformationHintText(text: String?)
    fun setupInformationColor(color: Int)
    fun setupInformationColorId(@ColorRes colorId: Int)

    fun showErrorHintText(errorText: String?)
    fun showErrorHintText(@StringRes errorText: Int)
    fun setupErrorColor(color: Int)

    fun setErrorColorId(@ColorRes colorId: Int)
    fun setSupportHintAppearance(@StyleRes style: Int?)
}
