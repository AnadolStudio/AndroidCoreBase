package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class UpperCaseValidator(
        @StringRes errorMessage: Int,
) : CharValidator(
        condition = { char -> char.isLetter() && char.isUpperCase() },
        errorMessage = errorMessage,
)
