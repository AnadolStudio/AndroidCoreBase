package com.anadolstudio.view.basetextinput.validator.mapper

import com.anadolstudio.view.basetextinput.validator.ValidatorUnit

interface ValidateMapper : ValidatorUnit {

    fun map(text: String): String
}
