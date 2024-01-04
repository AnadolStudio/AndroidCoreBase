package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class DiapasonLengthValidator(
        private val minLength: Int,
        private val maxLength: Int,
        @StringRes errorMessage: Int,
) : SimpleValidator(
        condition = { text -> text.length in minLength..maxLength },
        errorMessage = errorMessage,
)
