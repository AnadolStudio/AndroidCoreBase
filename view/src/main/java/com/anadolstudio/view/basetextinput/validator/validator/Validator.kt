package com.anadolstudio.view.basetextinput.validator.validator

import com.anadolstudio.view.basetextinput.validator.ValidatorUnit
import com.anadolstudio.view.basetextinput.validator.listener.ValidatorListener

interface Validator : ValidatorUnit, Validatable {

    override fun validate(text: String): ValidateResult

    fun addListener(listener: ValidatorListener)

    fun removeListener(listener: ValidatorListener)

}
