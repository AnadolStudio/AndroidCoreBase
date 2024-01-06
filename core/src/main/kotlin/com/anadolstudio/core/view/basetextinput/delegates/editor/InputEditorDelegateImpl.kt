package com.anadolstudio.core.view.basetextinput.delegates.editor

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.anadolstudio.core.util.common_extention.onTrue
import com.anadolstudio.core.util.common_extention.setMargins
import com.anadolstudio.core.view.basetextinput.delegates.support_hint.InputSupportHintDelegate
import com.anadolstudio.core.view.basetextinput.delegates.support_hint.InputSupportHintDelegateImpl
import com.anadolstudio.core.view.basetextinput.delegates.support_hint.ShowSupportHintMode
import com.anadolstudio.core.view.basetextinput.delegates.support_hint.SupportHintState
import com.google.android.material.textfield.TextInputLayout
import com.anadolstudio.core.view.basetextinput.BaseTextInputView
import com.anadolstudio.core.view.common.FocusChangeListenerContainer
import com.anadolstudio.core.view.common.TextWatcherContainer
import com.anadolstudio.core.view.common.TouchEventListenerContainer

@Suppress("detekt.TooManyFunctions")
class InputEditorDelegateImpl : InputEditorDelegate {

    private companion object {
        const val DRAWABLE_END = 2 // Индекс правого drawable в compoundDrawables TextView
        const val TEXT_INPUT_LAYOUT_TAG = "text_input_layout"
        const val EDIT_TEXT_TAG = "edit_text"
        const val UNDERLINE_TAG = "underline"
        const val SUPPORT_HINT_TAG = "support_hint"
        const val SUPPORT_HINT_START_IMAGE_TAG = "support_hint_start_image"
    }

    private var hintColor: Int = InputSupportHintDelegate.DEFAULT_ERROR_COLOR

    private lateinit var textInputLayout: TextInputLayout
    private lateinit var supportHintTextView: TextView
    private lateinit var supportHintStartImage: ImageView
    private lateinit var editText: EditText
    private lateinit var underline: View

    private var onIconClickListener: (() -> Unit)? = null

    private val focusChangeListenerContainer = FocusChangeListenerContainer()
    private val textWatcherContainer = TextWatcherContainer()
    private val touchEventListenerContainer = TouchEventListenerContainer()

    private val supportHintDelegate = InputSupportHintDelegateImpl()

    @SuppressLint("ClickableViewAccessibility")
    override fun bindWithBaseTextInputView(baseTextInputView: BaseTextInputView) {
        textInputLayout = baseTextInputView.findViewWithTag(TEXT_INPUT_LAYOUT_TAG)
        editText = baseTextInputView.findViewWithTag(EDIT_TEXT_TAG)
        underline = baseTextInputView.findViewWithTag(UNDERLINE_TAG)
        supportHintTextView = baseTextInputView.findViewWithTag(SUPPORT_HINT_TAG)
        supportHintStartImage = baseTextInputView.findViewWithTag(SUPPORT_HINT_START_IMAGE_TAG)

        textInputLayout.id = View.generateViewId()
        editText.id = View.generateViewId()
        underline.id = View.generateViewId()
        supportHintTextView.id = View.generateViewId()
        supportHintStartImage.id = View.generateViewId()

        supportHintDelegate.init(textInputLayout, underline, this, supportHintTextView, supportHintStartImage)

        editText.onFocusChangeListener = focusChangeListenerContainer
        editText.setOnTouchListener(touchEventListenerContainer)
        editText.addTextChangedListener(textWatcherContainer)

        baseTextInputView.addValidateListener(
                onSuccess = {
                    supportHintDelegate.clearCurrentError()
                },
                onFailure = { _, errorId ->
                    clearSupportHintStartDrawable()
                    showErrorHintText(errorId)
                }
        )
    }

    override fun getEditText(): EditText = editText

    override fun setText(text: String?) = editText.setText(text)

    override fun getRawText(): String = editText.text?.toString().orEmpty()

    override fun getSelectionStart(): Int = editText.selectionStart

    override fun setSelectionStart(index: Int) = editText.setSelection(index)

    override fun setSelectorToEnd() = editText.setSelection(getRawText().length)

    override fun setHint(text: String?) {
        textInputLayout.hint = text
    }

    override fun setHintColor(color: Int) {
        hintColor = color
        textInputLayout.defaultHintTextColor = ColorStateList.valueOf(color)
    }

    override fun setHintColorId(@ColorRes colorId: Int) = setHintColor(editText.context.getColor(colorId))

    override fun setHintAppearance(@StyleRes style: Int?) {
        if (style == null) return
        textInputLayout.setHintTextAppearance(style)
    }

    override fun getCurrentHintTextColor(): Int = editText.currentTextColor

    override fun setFilters(vararg filters: InputFilter) {
        editText.filters = editText.filters
                .toMutableList()
                .apply { addAll(filters) }
                .toTypedArray()
    }

    override fun setLongClickable(longClickable: Boolean) {
        editText.isLongClickable = longClickable
    }

    override fun setEnabled(enabled: Boolean) {
        editText.isEnabled = enabled
    }

    override fun setOnEditorActionListener(listener: TextView.OnEditorActionListener) {
        editText.setOnEditorActionListener(listener)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setOnTouchListener(listener: View.OnTouchListener?) {
        touchEventListenerContainer.setMainTouchEventListener(listener)
    }

    override fun setSupportTouchListener(listener: View.OnTouchListener) {
        touchEventListenerContainer.add(listener)
    }

    override fun setFocusable(focusable: Boolean) {
        editText.isFocusable = focusable
    }

    override fun setFocusableInTouchMode(focusable: Boolean) {
        editText.isFocusableInTouchMode = focusable
    }

    override fun hasFocus(): Boolean = editText.hasFocus()

    override fun clearFocus() = editText.clearFocus()

    override fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean =
            editText.requestFocus(direction, previouslyFocusedRect)

    @Suppress("ClickableViewAccessibility", "LabeledExpression")
    override fun setOnIconClickListener(listener: (() -> Unit)?) {
        onIconClickListener = listener

        editText.setOnTouchListener { _, event ->
            return@setOnTouchListener isEndDrawableIconClicked(event)
                    .onTrue { onIconClickListener?.invoke() }
        }
    }

    override fun isEndDrawableIconClicked(event: MotionEvent): Boolean {
        val drawableWidth = editText.compoundDrawables[DRAWABLE_END]?.bounds
                ?.width()
                ?: return false

        val drawableStartX = editText.width - drawableWidth - editText.paddingEnd
        // TODO посмотреть как реализовать там на Icon в CardDav, возможно, можно лучше
        return event.action == MotionEvent.ACTION_UP && event.rawX >= drawableStartX
    }

    override fun setOnFocusChangeListener(listener: View.OnFocusChangeListener?) { // TODO проблема нейминга set/add
        listener?.let(focusChangeListenerContainer::add)
    }

    override fun removeOnFocusChangeListener(listener: View.OnFocusChangeListener) {
        focusChangeListenerContainer.remove(listener)
    }

    override fun setTextColor(color: Int) {
        editText.setTextColor(color)
    }

    override fun setTextAppearance(@StyleRes style: Int?) {
        if (style == null) return
        editText.setTextAppearance(style)
    }

    override fun setInputType(type: Int) {
        editText.inputType = type
    }

    override fun setImeOptions(imeOptions: Int) {
        editText.imeOptions = imeOptions
    }

    override fun setDigits(digit: String) {
        if (digit.isBlank()) return
        editText.keyListener = DigitsKeyListener.getInstance(digit)
    }

    override fun setMaxLines(maxLines: Int) {
        editText.maxLines = maxLines
    }

    override fun setMaxLength(maxLength: Int?) {
        maxLength ?: return
        editText.filters = arrayOf(InputFilter.LengthFilter(maxLength))
    }

    override fun setDrawableEnd(@DrawableRes drawable: Int) {
        editText.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
    }

    override fun setDrawableEnd(drawable: Drawable?) {
        editText.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null)
    }

    override fun setEditTextBackgroundTint(color: Int) {
        editText.backgroundTintList = ColorStateList.valueOf(color)
    }

    override fun setEditTextBackgroundTintId(@ColorRes colorId: Int) = setEditTextBackgroundTint(editText.context.getColor(colorId))

    override fun addTextChangedListener(watcher: TextWatcher) {
        textWatcherContainer.add(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher) {
        textWatcherContainer.remove(watcher)
    }

    override fun setEditTextMargin(start: Int, top: Int, end: Int, bottom: Int) =
            editText.setMargins(start, top, end, bottom)

    override fun setEditTextMargin(margin: Int) = setEditTextMargin(margin, margin, margin, margin)

    override fun setEditTextPadding(start: Int, top: Int, end: Int, bottom: Int) =
            editText.setPadding(start, top, end, bottom)

    override fun setEditTextPadding(padding: Int) = setEditTextPadding(padding, padding, padding, padding)

    override fun getEditTextPaddingStart(): Int = editText.paddingStart

    override fun getEditTextPaddingEnd(): Int = editText.paddingEnd

    override fun getEditTextPaddingTop(): Int = editText.paddingTop

    override fun getEditTextPaddingBottom(): Int = editText.paddingBottom

    // START SupportHint delegate region
    override fun showSupportHint() = supportHintDelegate.showSupportHint()

    override fun setInformationHintText(text: String?) = supportHintDelegate.setInformationHintText(text)

    override fun setSupportHintMode(mode: ShowSupportHintMode) = supportHintDelegate.setSupportHintMode(mode)

    override fun supportHintIsShow(): Boolean = supportHintDelegate.supportHintIsShow()

    override fun getSupportHintText(): String? = supportHintDelegate.getSupportHintText()

    override fun setSupportHintAppearance(@StyleRes style: Int?) = supportHintDelegate.setSupportHintAppearance(style)

    override fun showErrorHintText(@StringRes errorText: Int) = showErrorHintText(editText.context.getString(errorText))

    override fun showErrorHintText(errorText: String?) = supportHintDelegate.showErrorHintText(errorText)

    override fun getSupportHintState(): SupportHintState = supportHintDelegate.getSupportHintState()

    override fun hasError(): Boolean = supportHintDelegate.hasError()

    override fun setSupportHintText(text: String?) = supportHintDelegate.setSupportHintText(text)

    override fun setSupportHintText(text: String?, state: SupportHintState) = supportHintDelegate.setSupportHintText(text, state)

    override fun setSupportHintText(textId: Int) = supportHintDelegate.setSupportHintText(textId)

    override fun setSupportHintTextColor(color: Int) = supportHintDelegate.setSupportHintTextColor(color)

    override fun setSupportHintTextColorId(colorId: Int) = supportHintDelegate.setSupportHintTextColorId(colorId)

    override fun setSupportHintStartDrawable(drawableId: Int) = supportHintDelegate.setSupportHintStartDrawable(drawableId)

    override fun setSupportHintStartDrawable(drawable: Drawable?) = supportHintDelegate.setSupportHintStartDrawable(drawable)

    override fun setSupportHintStartDrawable(drawable: Drawable?, sizePx: Int?, gravityInt: Int?, marginPx: Int?) =
            supportHintDelegate.setSupportHintStartDrawable(drawable, sizePx, gravityInt, marginPx)

    override fun clearSupportHintStartDrawable() = supportHintDelegate.clearSupportHintStartDrawable()

    override fun showInformationHint() = supportHintDelegate.showInformationHint()

    override fun showInformationHint(text: String?) = supportHintDelegate.showInformationHint(text)

    override fun setupInformationColor(color: Int) = supportHintDelegate.setupInformationColor(color)

    override fun setupInformationColorId(colorId: Int) = supportHintDelegate.setupInformationColor(colorId)

    override fun setupErrorColor(color: Int) = supportHintDelegate.setupErrorColor(color)

    override fun setErrorColorId(@ColorRes colorId: Int) = setupErrorColor(editText.context.getColor(colorId))

    override fun isFloating(isFloating: Boolean) = supportHintDelegate.isFloating(isFloating)

    override fun hideSupportHint() = supportHintDelegate.hideSupportHint()

    override fun setSupportHintEnable(isEnable: Boolean) = supportHintDelegate.setSupportHintEnable(isEnable)
    // End SupportHint delegate region

}
