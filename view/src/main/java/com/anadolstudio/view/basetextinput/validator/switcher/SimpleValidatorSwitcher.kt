package com.anadolstudio.view.basetextinput.validator.switcher

import com.anadolstudio.view.basetextinput.validator.validator.Validator

class SimpleValidatorSwitcher(private val currentValidator: () -> Validator) : ValidatorSwitcher {

    override fun getValidator(text: String): Validator = currentValidator.invoke()

}
