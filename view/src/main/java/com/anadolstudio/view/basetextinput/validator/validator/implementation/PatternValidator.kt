package com.anadolstudio.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes
import java.util.regex.Pattern

open class PatternValidator(
        private val pattern: Pattern,
        @StringRes val errorMessage: Int,
) : SimpleValidator(
        condition = { text -> pattern.matcher(text).find() },
        errorMessage = errorMessage,
)
