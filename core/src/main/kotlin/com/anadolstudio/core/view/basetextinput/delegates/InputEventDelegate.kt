package com.anadolstudio.core.view.basetextinput.delegates

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.anadolstudio.core.view.basetextinput.GlobalEditTextEventListener
import com.anadolstudio.core.view.basetextinput.delegates.editor.InputEditorDelegate
import com.anadolstudio.core.view.basetextinput.delegates.validator.InputValidatorDelegate
import com.anadolstudio.core.view.basetextinput.delegates.validator.InputValidatorDelegate.Companion.FLAG_AFTER_TEXT_CHANGED
import com.anadolstudio.core.view.basetextinput.delegates.validator.InputValidatorDelegate.Companion.FLAG_BEFORE_TEXT_CHANGED
import com.anadolstudio.core.view.basetextinput.delegates.validator.InputValidatorDelegate.Companion.FLAG_HAS_FOCUS
import com.anadolstudio.core.view.basetextinput.delegates.validator.InputValidatorDelegate.Companion.FLAG_LOSS_FOCUS
import com.anadolstudio.core.view.basetextinput.delegates.validator.InputValidatorDelegate.Companion.FLAG_ON_TEXT_CHANGED

class InputEventDelegate {

    // TODO перекомпановать инпут: InputEventDelegate должен отвечает только за поставку событий,
    //  "handle" должен быть в другом месте

    private lateinit var globalEditTextEventListener: GlobalEditTextEventListener

    fun initEvents(validatorDelegate: InputValidatorDelegate, editorDelegate: InputEditorDelegate) {
        setupGlobalEventListener(validatorDelegate)
        bindWithEditDelegateEvents(editorDelegate)
    }

    private fun bindWithEditDelegateEvents(editorDelegate: InputEditorDelegate) {
        editorDelegate.setOnFocusChangeListener(globalEditTextEventListener::onFocusChange)

        editorDelegate.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) =
                    globalEditTextEventListener.beforeTextChanged(text, start, count, after)

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) =
                    globalEditTextEventListener.onTextChanged(text, start, before, count)

            override fun afterTextChanged(text: Editable?) =
                    globalEditTextEventListener.afterTextChanged(text)
        })
    }

    private fun setupGlobalEventListener(validatorDelegate: InputValidatorDelegate) {
        globalEditTextEventListener = object : GlobalEditTextEventListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) = when (hasFocus) {
                true -> handleEditEvent(FLAG_HAS_FOCUS, validatorDelegate)
                false -> handleEditEvent(FLAG_LOSS_FOCUS, validatorDelegate)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                    handleEditEvent(FLAG_BEFORE_TEXT_CHANGED, validatorDelegate)

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) =
                    handleEditEvent(FLAG_ON_TEXT_CHANGED, validatorDelegate)

            override fun afterTextChanged(s: Editable?) =
                    handleEditEvent(FLAG_AFTER_TEXT_CHANGED, validatorDelegate)
        }
    }

    private fun handleEditEvent(event: Int, validateDelegate: InputValidatorDelegate) {
        when (event and validateDelegate.getValidateModeFlag()) {
            FLAG_HAS_FOCUS,
            FLAG_LOSS_FOCUS,
            FLAG_BEFORE_TEXT_CHANGED,
            FLAG_ON_TEXT_CHANGED,
            FLAG_AFTER_TEXT_CHANGED -> {
                validateDelegate.validate()
            }
        }
    }

}
