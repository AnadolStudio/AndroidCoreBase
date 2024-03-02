package com.anadolstudio.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

open class EmptyValidator(
        @StringRes errorMessage: Int,
) : SimpleValidator(
        condition = { text -> text.isNotEmpty() },
        errorMessage = errorMessage,
)
