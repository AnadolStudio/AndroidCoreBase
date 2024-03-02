package com.anadolstudio.view.basetextinput.validator.listener.implementation

import com.anadolstudio.view.basetextinput.validator.listener.ValidatorListener
import com.anadolstudio.view.basetextinput.validator.validator.ValidateResult

open class SuccessValidatorListener(private val onValidate: (String) -> Unit) : ValidatorListener {

    override fun onValidate(result: ValidateResult) {
        if (result is ValidateResult.Success) {
            onValidate.invoke(result.data)
        }
    }
}
