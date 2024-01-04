package com.anadolstudio.core.view.basetextinput.validator.validator

interface Validatable {

    fun validate(text: String): ValidateResult
}

