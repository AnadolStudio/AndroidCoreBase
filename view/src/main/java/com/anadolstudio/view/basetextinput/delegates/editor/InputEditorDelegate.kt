package com.anadolstudio.view.basetextinput.delegates.editor

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import com.anadolstudio.view.basetextinput.BaseTextInputView
import com.anadolstudio.view.basetextinput.delegates.support_hint.InputSupportHintDelegate

interface InputEditorDelegate: InputSupportHintDelegate {

    fun bindWithBaseTextInputView(baseTextInputView: BaseTextInputView)
    fun getEditText(): EditText

    fun getRawText(): String
    fun setText(text: String?)
    fun getSelectionStart():Int
    fun getSelectionEnd():Int
    fun setSelectionStart(index: Int)
    fun setSelectorToEnd()

    fun setHint(text: String?)
    fun setHintColor(color: Int)
    fun setHintColorId(@ColorRes colorId: Int)
    fun setHintAppearance(@StyleRes style: Int?)

    fun getCurrentHintTextColor(): Int

    fun setFilters(vararg filters: InputFilter)
    fun setLongClickable(longClickable: Boolean)
    fun setEnabled(enabled: Boolean)
    fun setOnEditorActionListener(listener: TextView.OnEditorActionListener)
    fun setOnTouchListener(listener: View.OnTouchListener?)
    fun setSupportTouchListener(listener: View.OnTouchListener)

    fun isEndDrawableIconClicked(event: MotionEvent): Boolean
    fun setFocusable(focusable: Boolean)
    fun setFocusableInTouchMode(focusable: Boolean)
    fun hasFocus(): Boolean
    fun clearFocus()
    fun setOnIconClickListener(listener: (() -> Unit)?)
    fun requestFocus(direction: Int, previouslyFocusedRect: Rect?): Boolean
    fun setOnFocusChangeListener(listener: View.OnFocusChangeListener?)
    fun removeOnFocusChangeListener(listener: View.OnFocusChangeListener)

    fun setTextColor(color: Int)
    fun setTextAppearance(@StyleRes style: Int?)

    fun setInputType(type: Int)
    fun setImeOptions(imeOptions: Int)

    fun setDigits(digit: String)

    fun setMaxLines(maxLines: Int)
    fun setMaxLength(maxLength: Int?)
    fun setDrawableEnd(@DrawableRes drawable: Int)
    fun setDrawableEnd(drawable: Drawable?)

    fun setEditTextBackgroundTint(color: Int)
    fun setEditTextBackgroundTintId(@ColorRes colorId: Int)

    fun addTextChangedListener(watcher: TextWatcher)
    fun removeTextChangedListener(watcher: TextWatcher)

    fun setEditTextMargin(margin: Int)
    fun setEditTextMargin(start: Int, top: Int, end: Int, bottom: Int)

    fun setEditTextPadding(start: Int, top: Int, end: Int, bottom: Int)
    fun setEditTextPadding(padding: Int)

    fun getEditTextPaddingStart(): Int
    fun getEditTextPaddingEnd(): Int
    fun getEditTextPaddingTop(): Int
    fun getEditTextPaddingBottom(): Int

}
