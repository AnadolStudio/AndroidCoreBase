package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class PhoneValidator(
        @StringRes errorMessage: Int,
) : RegexValidator(
        regex = PHONE_PATTERN.toRegex(),
        errorMessage = errorMessage,
) {
    private companion object {
        const val PHONE_PATTERN = "\\d{11}"
    }
}
