package com.anadolstudio.view.basetextinput.validator.switcher

import com.anadolstudio.view.basetextinput.validator.validator.Validator

class TextValidatorSwitcher(private val currentValidator: (text: String) -> Validator) : ValidatorSwitcher {

    override fun getValidator(text: String): Validator = currentValidator.invoke(text)

}
