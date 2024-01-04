package com.anadolstudio.core.view.basetextinput.validator.switcher

import com.anadolstudio.core.view.basetextinput.validator.validator.Validator

class SimpleValidatorSwitcher(private val currentValidator: () -> Validator) : ValidatorSwitcher {

    override fun getValidator(text: String): Validator = currentValidator.invoke()

}
