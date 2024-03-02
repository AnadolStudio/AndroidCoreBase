package com.anadolstudio.view.basetextinput.validator.listener.implementation

import com.anadolstudio.view.basetextinput.validator.listener.ValidatorListener
import com.anadolstudio.view.basetextinput.validator.validator.ValidateResult

open class SimpleValidatorListener(private val onValidate: (ValidateResult) -> Unit) : ValidatorListener {

    override fun onValidate(result: ValidateResult) {
        onValidate.invoke(result)
    }
}
