package com.anadolstudio.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class LatinValidator(
        @StringRes errorMessage: Int,
) : RegexValidator(
        regex = LATIN_PATTERN.toRegex(),
        errorMessage = errorMessage,
) {
    private companion object {
        const val LATIN_PATTERN = "^[a-zA-Z]+\$"
    }
}
