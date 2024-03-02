package com.anadolstudio.view.basetextinput.validator.switcher

import com.anadolstudio.view.basetextinput.validator.ValidatorUnit
import com.anadolstudio.view.basetextinput.validator.validator.Validatable
import com.anadolstudio.view.basetextinput.validator.validator.ValidateResult
import com.anadolstudio.view.basetextinput.validator.validator.Validator

interface ValidatorSwitcher : ValidatorUnit, Validatable {

    fun getValidator(text: String): Validator

    override fun validate(text: String): ValidateResult = getValidator(text).validate(text)
}
