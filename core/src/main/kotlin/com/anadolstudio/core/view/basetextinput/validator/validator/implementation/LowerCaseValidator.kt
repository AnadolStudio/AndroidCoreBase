package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class LowerCaseValidator(
        @StringRes errorMessage: Int,
) : CharValidator(
        condition = { char -> char.isLetter() && char.isLowerCase() },
        errorMessage = errorMessage,
)
