package com.anadolstudio.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes
import org.joda.time.DateTime

class MaxDateValidator(
        private val maxDate: Long,
        @StringRes val errorMessage: Int,
) : SimpleValidator(
        condition = { text ->
            text.toLongOrNull()
                    ?.let { dateInMills -> DateTime(dateInMills).isBefore(maxDate) }
                    ?: false
        },
        errorMessage = errorMessage,
)
