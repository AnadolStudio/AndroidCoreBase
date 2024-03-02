package com.anadolstudio.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class LengthValidator(
        private val length: Int,
        @StringRes errorMessage: Int,
) : SimpleValidator(
        condition = { text -> text.length == length },
        errorMessage = errorMessage,
)
