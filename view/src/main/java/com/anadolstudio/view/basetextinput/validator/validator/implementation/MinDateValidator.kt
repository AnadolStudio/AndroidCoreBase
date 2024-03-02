package com.anadolstudio.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes

class MinDateValidator(
        private val minDate: Long,
        @StringRes val errorMessage: Int,
) : SimpleValidator(
        condition = { text ->
            text.toLongOrNull()
                    ?.let { dateInMills -> dateInMills >= minDate }
                    ?: false
        },
        errorMessage = errorMessage,
)
