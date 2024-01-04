package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes
import com.anadolstudio.core.view.basetextinput.validator.validator.ValidateResult

@Suppress("detekt.LabeledExpression")
open class CharValidator(
        private val condition: (Char) -> Boolean,
        @StringRes private val errorMessage: Int,
) : BasePredicateValidator(
        predicate = { text ->
            var result = true

            run breaking@{
                text.forEach { char ->
                    if (!condition(char)) {
                        result = false
                        return@breaking
                    }
                }
            }

            when (result) {
                true -> ValidateResult.Success(text)
                false -> ValidateResult.Error(text, errorMessage)
            }
        }
)
