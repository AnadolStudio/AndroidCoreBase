package com.anadolstudio.core.view.basetextinput.validator.switcher

import com.anadolstudio.core.view.basetextinput.validator.ValidatorUnit
import com.anadolstudio.core.view.basetextinput.validator.validator.Validatable
import com.anadolstudio.core.view.basetextinput.validator.validator.ValidateResult
import com.anadolstudio.core.view.basetextinput.validator.validator.Validator

interface ValidatorSwitcher : ValidatorUnit, Validatable {

    fun getValidator(text: String): Validator

    override fun validate(text: String): ValidateResult = getValidator(text).validate(text)
}
