package com.anadolstudio.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class TimeValidator(
        @StringRes errorMessage: Int,
) : RegexValidator(
        regex = TIME_PATTERN.toRegex(),
        errorMessage = errorMessage,
) {
    private companion object {
        const val TIME_PATTERN = "^([0-1]?[0-9]|2[0-3]):[0-5][0-9]\$"
    }
}
