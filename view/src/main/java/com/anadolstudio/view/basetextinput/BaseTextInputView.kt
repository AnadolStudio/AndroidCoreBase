package com.anadolstudio.view.basetextinput

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.os.Parcelable
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.withStyledAttributes
import com.anadolstudio.view.R
import com.anadolstudio.view.basetextinput.validator.validator.Validator
import com.anadolstudio.view.basetextinput.validator.validator.implementation.CyrillicValidator
import com.anadolstudio.view.basetextinput.validator.validator.implementation.DateFormatValidator
import com.anadolstudio.view.basetextinput.validator.validator.implementation.EmailValidator
import com.anadolstudio.view.basetextinput.validator.validator.implementation.EmptyValidator
import com.anadolstudio.view.basetextinput.validator.validator.implementation.LatinValidator
import com.anadolstudio.view.basetextinput.validator.validator.implementation.NumberValidator
import com.anadolstudio.view.basetextinput.validator.validator.implementation.PhoneValidator
import com.anadolstudio.view.basetextinput.validator.validator.implementation.TimeValidator
import com.anadolstudio.view.basetextinput.delegates.InputEventDelegate
import com.anadolstudio.view.basetextinput.delegates.editor.InputEditorDelegate
import com.anadolstudio.view.basetextinput.delegates.editor.InputEditorDelegateImpl
import com.anadolstudio.view.basetextinput.delegates.support_hint.InputSupportHintDelegate
import com.anadolstudio.view.basetextinput.delegates.support_hint.ShowSupportHintMode
import com.anadolstudio.view.basetextinput.delegates.support_hint.SupportHintState
import com.anadolstudio.view.basetextinput.delegates.validator.InputValidatorDelegate
import com.anadolstudio.view.basetextinput.delegates.validator.InputValidatorDelegateImpl
import com.anadolstudio.view.basetextinput.delegates.validator.ValidatorTypes

@Suppress("TooManyFunctions")
open class BaseTextInputView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), InputValidatorDelegate by InputValidatorDelegateImpl(),
        InputEditorDelegate by InputEditorDelegateImpl() {

    private companion object {
        const val NO_RESOURCE = -1
        const val MAX_LENGTH_FOR_SINGLE_LINE_EDIT_TEXT = 5000
        const val ONE_LINE_EDIT_TEXT = 1
        const val NONE = 0
    }

    private val eventDelegate = InputEventDelegate()

    init {
        LayoutInflater.from(context).inflate(R.layout.view_base_input_text, this, true)
        initDelegates()

        context.withStyledAttributes(attrs, R.styleable.BaseTextInputView, defStyleAttr, 0) {
            setupEditOptions()
            setupEditTextPadding()
            setupHint()
            setupSupportHint()
            val errorMessageId = setupError()
            setupText()

            setValidator(
                    createValidatorFromAttr(
                            index = getInt(R.styleable.BaseTextInputView_validator, NONE),
                            errorId = errorMessageId
                    )
            )
            setValidatorMode(getInt(R.styleable.BaseTextInputView_validatorMode, InputValidatorDelegate.FLAG_NONE))
        }
    }

    private fun TypedArray.setupText() {
        setText(getString(R.styleable.BaseTextInputView_editText))
        setTextColor(getColor(R.styleable.BaseTextInputView_editTextColor, Color.BLACK))
        val appearance = getResourceId(R.styleable.BaseTextInputView_editTextAppearance, NO_RESOURCE)
        setTextAppearance(if (appearance == NO_RESOURCE) null else appearance)
        setEditTextBackgroundTint(getColor(R.styleable.BaseTextInputView_editTextBackgroundTint, getCurrentHintTextColor()))
    }

    private fun TypedArray.setupError(): Int {
        setSupportHintEnable(getBoolean(R.styleable.BaseTextInputView_errorEnabled, false))
        val errorMessageId = getResourceId(R.styleable.BaseTextInputView_error, NO_RESOURCE)

        showErrorHintText(if (errorMessageId == NO_RESOURCE) null else context.getString(errorMessageId))
        setupErrorColor(getColor(R.styleable.BaseTextInputView_errorTextColor, InputSupportHintDelegate.DEFAULT_ERROR_COLOR))
        setSupportHintAppearance(getResourceId(R.styleable.BaseTextInputView_errorTextAppearance, NO_RESOURCE))
        isFloating(getBoolean(R.styleable.BaseTextInputView_floatError, true))

        return errorMessageId
    }

    private fun TypedArray.setupSupportHint() {
        setInformationHintText(getString(R.styleable.BaseTextInputView_informationHint))
        val index = getInt(R.styleable.BaseTextInputView_showInformationHintMode, ShowSupportHintMode.PERMANENT.ordinal)
        val informationHintColor = getColor(R.styleable.BaseTextInputView_informationHintTextColor, InputSupportHintDelegate.DEFAULT_HINT_COLOR)
        setupInformationColor(informationHintColor)
        val mode = ShowSupportHintMode.values()[index]
        setSupportHintMode(mode)
    }

    // TODO Сделайть ренейминг на label
    private fun TypedArray.setupHint() {
        setHint(getString(R.styleable.BaseTextInputView_hint))
        val colorHint = getColor(R.styleable.BaseTextInputView_hintTextColor, InputSupportHintDelegate.DEFAULT_HINT_COLOR)
        setHintColor(colorHint)
        val appearance = getResourceId(R.styleable.BaseTextInputView_hintTextAppearance, NO_RESOURCE)
        setHintAppearance(if (appearance == NO_RESOURCE) null else appearance)
    }

    private fun TypedArray.setupEditTextPadding() {
        val editTextPadding = getDimensionPixelSize(R.styleable.BaseTextInputView_editTextPadding, NO_RESOURCE)
        val editTextPaddingStart = getDimensionPixelSize(R.styleable.BaseTextInputView_editTextPaddingStart, getEditTextPaddingStart())
        val editTextPaddingEnd = getDimensionPixelSize(R.styleable.BaseTextInputView_editTextPaddingEnd, getEditTextPaddingEnd())
        val editTextPaddingTop = getDimensionPixelSize(R.styleable.BaseTextInputView_editTextPaddingTop, getEditTextPaddingTop())
        val editTextPaddingBottom = getDimensionPixelSize(R.styleable.BaseTextInputView_editTextPaddingBottom, getEditTextPaddingBottom())

        if (editTextPadding == NO_RESOURCE) {
            setEditTextPadding(
                    start = editTextPaddingStart,
                    end = editTextPaddingEnd,
                    top = editTextPaddingTop,
                    bottom = editTextPaddingBottom
            )
        } else {
            setEditTextPadding(editTextPadding)
        }
    }

    private fun TypedArray.setupEditOptions() {
        setImeOptions(getInt(R.styleable.BaseTextInputView_imeOptions, EditorInfo.IME_NULL))
        setInputType(getInt(R.styleable.BaseTextInputView_inputType, InputType.TYPE_CLASS_TEXT))
        setDigits(getString(R.styleable.BaseTextInputView_digits).orEmpty())
        setDrawableEnd(getDrawable(R.styleable.BaseTextInputView_drawableEnd))
        setMaxLength(getInt(R.styleable.BaseTextInputView_maxLength, MAX_LENGTH_FOR_SINGLE_LINE_EDIT_TEXT))
        setMaxLines(getInt(R.styleable.BaseTextInputView_maxLines, ONE_LINE_EDIT_TEXT))

        isFocusable = getBoolean(R.styleable.BaseTextInputView_editTextFocusable, false)
        isFocusableInTouchMode = getBoolean(R.styleable.BaseTextInputView_editTextFocusableInTouchMode, true)
        isLongClickable = getBoolean(R.styleable.BaseTextInputView_editTextLongClickable, true)
    }

    private fun createValidatorFromAttr(index: Int, @StringRes errorId: Int): Validator? {
        val validatorType = ValidatorTypes.values()[index]

        return when (validatorType) {
            ValidatorTypes.NONE -> null
            ValidatorTypes.CYRILLIC -> CyrillicValidator(errorId)
            ValidatorTypes.PHONE -> PhoneValidator(errorId)
            ValidatorTypes.DATE -> DateFormatValidator(errorId)
            ValidatorTypes.TIME -> TimeValidator(errorId)
            ValidatorTypes.EMPTY -> EmptyValidator(errorId)
            ValidatorTypes.EMAIL -> EmailValidator(errorId)
            ValidatorTypes.LATIN -> LatinValidator(errorId)
            ValidatorTypes.NUMBER -> NumberValidator(errorId)
        }
    }

    private fun initDelegates() {
        bindWithBaseTextInputView(this)
        bindWithEditorDelegate(this)
        eventDelegate.initEvents(validatorDelegate = this, editorDelegate = this)
    }

    @Suppress("detekt.UnnecessaryApply")
    override fun onSaveInstanceState(): Parcelable? = super.onSaveInstanceState()?.let { superState ->
        BaseTextInputViewSavedState(superState).apply {
            saveData(
                    BaseTextInputViewSavedState.Data(
                            text = getRawText(),
                            supportHintText = getSupportHintText(),
                            supportHintState = getSupportHintState()
                    )
            )
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is BaseTextInputViewSavedState) {
            super.onRestoreInstanceState(state.superState)

            val restoreData = state.restoreData()
            setText(text = restoreData.text, withValidate = false)

            when (restoreData.supportHintState) {
                SupportHintState.INFORMATION -> showInformationHint(restoreData.supportHintText)
                SupportHintState.ERROR -> showErrorHintText(restoreData.supportHintText)
                else -> Unit
            }

        } else {
            super.onRestoreInstanceState(state)
        }
    }

    @Suppress("detekt.LabeledExpression", "ClickableViewAccessibility")
    fun onSimpleTouchActionUp(action: () -> Unit) = onTouchActionUp {
        action.invoke()

        return@onTouchActionUp true
    }

    @Suppress("detekt.LabeledExpression", "ClickableViewAccessibility")
    fun onTouchActionUp(action: (MotionEvent) -> Boolean) {
        setOnTouchListener { _, event ->
            return@setOnTouchListener when (event.action) {
                MotionEvent.ACTION_UP -> action.invoke(event)
                else -> false
            }
        }
    }

    fun setText(text: String?, withValidate: Boolean) = when (withValidate) {
        true -> setText(text).also { manualValidate() }
        false -> actionWithDisableValidate { setText(text) }
    }

    fun setTextColorId(@ColorRes colorId: Int) = setTextColor(context.getColor(colorId))

}
