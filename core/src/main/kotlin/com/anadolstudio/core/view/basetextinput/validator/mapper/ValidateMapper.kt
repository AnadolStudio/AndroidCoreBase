package com.anadolstudio.core.view.basetextinput.validator.mapper

import com.anadolstudio.core.view.basetextinput.validator.ValidatorUnit

interface ValidateMapper : ValidatorUnit {

    fun map(text: String): String
}
