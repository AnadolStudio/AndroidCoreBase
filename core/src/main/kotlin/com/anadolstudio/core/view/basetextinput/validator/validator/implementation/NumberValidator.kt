package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class NumberValidator(
        @StringRes errorMessage: Int,
) : RegexValidator(
        regex = NUMBER_PATTERN.toRegex(),
        errorMessage = errorMessage,
) {
    private companion object {
        const val NUMBER_PATTERN = "\\d+"
    }
}
