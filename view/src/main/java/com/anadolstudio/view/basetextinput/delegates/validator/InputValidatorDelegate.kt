package com.anadolstudio.view.basetextinput.delegates.validator

import com.anadolstudio.view.basetextinput.validator.listener.ValidatorListener
import com.anadolstudio.view.basetextinput.validator.validator.Validator
import com.anadolstudio.view.basetextinput.delegates.editor.InputEditorDelegate

interface InputValidatorDelegate {

    companion object {
        /**
         * <pre>
         * |-------|-------|-------|-------|
         *                                   FLAG_NONE
         *                                 1 FLAG_HAS_FOCUS
         *                                1  FLAG_LOSS_FOCUS
         *                               1   FLAG_BEFORE_TEXT_CHANGED
         *                              1    FLAG_ON_TEXT_CHANGED
         *                             1     FLAG_AFTER_TEXT_CHANGED
         * |-------|-------|-------|-------|</pre>
         */

        const val FLAG_NONE = 0b0000000000 // 0
        const val FLAG_HAS_FOCUS = 0b0000000001 // 1
        const val FLAG_LOSS_FOCUS = 0b0000000010 // 2
        const val FLAG_BEFORE_TEXT_CHANGED = 0b0000000100 // 4
        const val FLAG_ON_TEXT_CHANGED = 0b0000001000 // 8
        const val FLAG_AFTER_TEXT_CHANGED = 0b0000010000 // 16
    }

    fun bindWithEditorDelegate(inputEditorDelegate: InputEditorDelegate)

    fun manualValidate()

    fun validate()

    fun validate(validator: Validator)

    fun setNeedToValidateFlag(isNeed: Boolean)

    fun setValidator(validator: Validator?)

    fun setValidatorMode(flagMode: Int)

    fun getValidateModeFlag(): Int

    fun actionWithDisableValidate(action: () -> Unit)

    fun addValidateListener(listener: ValidatorListener)

    fun addSuccessOrNullValidateListener(listener: (String?) -> Unit)

    fun addValidateListener(
            onSuccess: ((String) -> Unit)? = null,
            onFailure: ((String, Int) -> Unit)? = null
    )

    fun hasNeedToValidateFlag(): Boolean
}

