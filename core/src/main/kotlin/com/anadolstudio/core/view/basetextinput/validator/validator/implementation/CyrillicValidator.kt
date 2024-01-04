package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class CyrillicValidator(
        @StringRes errorMessage: Int,
) : RegexValidator(
        regex = CYRILLIC_PATTERN.toRegex(),
        errorMessage = errorMessage,
) {
    private companion object {
        const val CYRILLIC_PATTERN = "^[а-яА-Я]+\$"
    }
}
