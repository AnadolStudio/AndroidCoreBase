package com.anadolstudio.core.view.basetextinput.delegates.support_hint

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setMargins
import com.anadolstudio.core.R
import com.anadolstudio.core.view.basetextinput.delegates.editor.InputEditorDelegateImpl
import com.google.android.material.textfield.TextInputLayout

@Suppress("detekt.TooManyFunctions")
class InputSupportHintDelegateImpl : InputSupportHintDelegate {

    private companion object {
        const val UNDERLINE_FOCUS_HEIGHT = 2.0F
        const val UNDERLINE_DEFAULT_HEIGHT = 1.0F
    }

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var supportHintTextView: TextView
    private lateinit var supportHintStartImage: ImageView
    private lateinit var underline: View
    private var informationHintColor: Int = InputSupportHintDelegate.DEFAULT_HINT_COLOR
    private var errorHintColor: Int = InputSupportHintDelegate.DEFAULT_ERROR_COLOR
    private var showSupportHintMode: ShowSupportHintMode = ShowSupportHintMode.PERMANENT
    private var informationHintText: String? = null
    private var supportHintIsShow = false
    private var isFloating = true

    private var currentState: SupportHintState = SupportHintState.INFORMATION
        set(value) {
            field = value
            updateUnderlineColor(value, textInputLayout.editText?.hasFocus() ?: false)
        }

    fun init(
            textInputView: TextInputLayout,
            underline: View,
            inputEditorDelegateImpl: InputEditorDelegateImpl,
            supportHintTextView: TextView,
            supportHintStartImage: ImageView
    ) {
        this.textInputLayout = textInputView
        this.underline = underline
        this.supportHintTextView = supportHintTextView
        this.supportHintStartImage = supportHintStartImage

        setupUnderline(inputEditorDelegateImpl)
    }

    private fun setupUnderline(inputEditorDelegateImpl: InputEditorDelegateImpl) {
        inputEditorDelegateImpl.setOnFocusChangeListener { _, hasFocus ->
            when (hasFocus) {
                true -> underline.scaleY = UNDERLINE_FOCUS_HEIGHT
                false -> underline.scaleY = UNDERLINE_DEFAULT_HEIGHT
            }
            updateUnderlineColor(currentState, hasFocus)
        }
    }

    override fun showSupportHint() {
        supportHintTextView.isVisible = true
    }

    override fun hideSupportHint() {
        supportHintTextView.text = null
        currentState = SupportHintState.EMPTY

        if (isFloating) {
            supportHintTextView.isVisible = false
        }
    }

    override fun supportHintIsShow(): Boolean = supportHintIsShow

    override fun isFloating(isFloating: Boolean) {
        this.isFloating = isFloating
    }

    override fun setSupportHintEnable(isEnable: Boolean) {
        supportHintTextView.isVisible = isEnable
        supportHintStartImage.isVisible = isEnable && supportHintStartImage.drawable != null
    }

    override fun setSupportHintMode(mode: ShowSupportHintMode) {
        showSupportHintMode = mode
    }

    override fun setSupportHintText(text: String?) {
        if (text.isNullOrEmpty()) {
            hideSupportHint()

            return
        }

        val color = when (currentState) {
            SupportHintState.ERROR -> errorHintColor
            SupportHintState.INFORMATION -> informationHintColor
            SupportHintState.EMPTY -> return
        }

        supportHintTextView.setTextColor(ColorStateList.valueOf(color))

        if (supportHintTextView.isVisible || isFloating) {

            supportHintTextView.isVisible = isFloating

            if (supportHintTextView.text.toString() != text) {
                // Чтобы не было мельканий
                supportHintTextView.text = text
            }
        }
    }

    override fun setSupportHintText(text: String?, state: SupportHintState) {
        currentState = state
        updateUnderlineColor(currentState, textInputLayout.editText?.hasFocus() ?: false)
        setSupportHintText(text)
    }

    override fun setSupportHintTextColor(color: Int) {
        val colorList = ColorStateList.valueOf(color)
        supportHintTextView.setTextColor(colorList)
        supportHintStartImage.imageTintList = colorList
    }

    override fun setSupportHintTextColorId(colorId: Int) =
            supportHintTextView.setTextColor(ColorStateList.valueOf(context().getColor(colorId)))

    override fun setSupportHintStartDrawable(drawableId: Int) =
            setSupportHintStartDrawable(ContextCompat.getDrawable(context(), drawableId))

    override fun setSupportHintStartDrawable(drawable: Drawable?) =
            setSupportHintStartDrawable(drawable, null, null, null)

    override fun setSupportHintStartDrawable(drawable: Drawable?, sizePx: Int?, gravityInt: Int?, marginPx: Int?) {
        supportHintStartImage.setImageDrawable(drawable)
        supportHintStartImage.isVisible = supportHintTextView.isVisible && drawable != null

        val layoutParams = supportHintStartImage.layoutParams as? LinearLayout.LayoutParams
                ?: return

        sizePx?.let { size ->
            layoutParams.width = size
            layoutParams.height = size
        }
        gravityInt?.let { gravity ->
            layoutParams.gravity = gravity
        }
        marginPx?.let { margin ->
            layoutParams.setMargins(margin)
        }

        supportHintStartImage.layoutParams = layoutParams
        supportHintStartImage.requestLayout()
    }

    override fun clearSupportHintStartDrawable() {
        supportHintStartImage.setImageDrawable(null)
        supportHintStartImage.isVisible = false
    }

    private fun context(): Context = textInputLayout.context

    private fun updateUnderlineColor(currentState: SupportHintState, hasFocus: Boolean) {
        val color = when {
            currentState == SupportHintState.ERROR -> errorHintColor
            hasFocus -> context().getColor(R.color.colorAccent)
            else -> informationHintColor
        }

        underline.background = ColorDrawable(color)
    }

    override fun getSupportHintState(): SupportHintState = currentState

    override fun hasError(): Boolean = currentState == SupportHintState.ERROR

    override fun setSupportHintText(@StringRes textId: Int) = setSupportHintText(context().getString(textId))

    override fun getSupportHintText(): String? = supportHintTextView.text?.toString()

    override fun setInformationHintText(text: String?) {
        informationHintText = text
    }

    override fun setupInformationColor(color: Int) {
        informationHintColor = color
    }

    override fun setupInformationColorId(colorId: Int) = setupInformationColor(context().getColor(colorId))

    override fun showInformationHint(text: String?) {
        informationHintText = text
        showInformationHint()
    }

    override fun showInformationHint() = setSupportHintText(informationHintText, SupportHintState.INFORMATION)

    override fun showErrorHintText(errorText: String?) = setSupportHintText(errorText, SupportHintState.ERROR)

    override fun showErrorHintText(errorText: Int) = showErrorHintText(context().getString(errorText))

    override fun setupErrorColor(color: Int) {
        errorHintColor = color
    }

    override fun setErrorColorId(colorId: Int) = setupErrorColor(context().getColor(colorId))

    override fun setSupportHintAppearance(style: Int?) {
        if (style == null) return
        supportHintTextView.setTextAppearance(style)
    }

    fun clearCurrentError() {
        val currentText = textInputLayout.editText?.text?.toString().orEmpty()

        when {
            informationHintText.isNullOrEmpty() -> hideSupportHint()
            showSupportHintMode == ShowSupportHintMode.PERMANENT -> showInformationHint()
            showSupportHintMode == ShowSupportHintMode.ON_EMPTY && currentText.isEmpty() -> showInformationHint()
            else -> hideSupportHint()
        }
    }
}
