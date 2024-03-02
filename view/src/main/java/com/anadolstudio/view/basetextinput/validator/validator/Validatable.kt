package com.anadolstudio.view.basetextinput.validator.validator

interface Validatable {

    fun validate(text: String): ValidateResult
}

