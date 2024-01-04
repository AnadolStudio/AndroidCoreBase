package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes
import com.anadolstudio.core.view.basetextinput.validator.validator.ValidateResult

open class SimpleValidator(
        private val condition: (String) -> Boolean,
        @StringRes private val errorMessage: Int,
) : BasePredicateValidator(
        predicate = { text ->
            when (condition.invoke(text)) {
                true -> ValidateResult.Success(text)
                false -> ValidateResult.Error(text, errorMessage)
            }
        }
)
