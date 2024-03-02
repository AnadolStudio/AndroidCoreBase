package com.anadolstudio.view.basetextinput.delegates.validator

import com.anadolstudio.view.basetextinput.validator.listener.implementation.SuccessOrNullValidatorListener
import com.anadolstudio.view.basetextinput.validator.validator.Validator
import com.anadolstudio.view.basetextinput.delegates.editor.InputEditorDelegate

class InputValidatorDelegateImpl : BaseInputValidatorDelegate() {

    private lateinit var inputEditorDelegate: InputEditorDelegate

    override fun bindWithEditorDelegate(inputEditorDelegate: InputEditorDelegate) {
        this.inputEditorDelegate = inputEditorDelegate
    }

    override fun validate(validator: Validator, isManualValidate: Boolean) {
        if (!needValidateFlag && !isManualValidate) return

        validator.validate(text = inputEditorDelegate.getRawText())
    }

    override fun addSuccessOrNullValidateListener(listener: (String?) -> Unit) = addValidateListener(
            SuccessOrNullValidatorListener { result -> listener.invoke(result) }
    )

    override fun actionWithDisableValidate(action: () -> Unit) {
        val previousNeedValidateFlag = needValidateFlag
        needValidateFlag = false
        action.invoke()
        needValidateFlag = previousNeedValidateFlag
    }
}
