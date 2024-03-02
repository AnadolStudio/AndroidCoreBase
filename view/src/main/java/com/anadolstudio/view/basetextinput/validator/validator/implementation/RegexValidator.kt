package com.anadolstudio.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

open class RegexValidator(
        private val regex: Regex,
        @StringRes val errorMessage: Int,
) : SimpleValidator(
        condition = { text -> regex.matches(text) },
        errorMessage = errorMessage
) {
    constructor(regex: String, @StringRes errorMessage: Int) : this(regex.toRegex(), errorMessage)
}
