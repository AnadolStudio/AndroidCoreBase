package com.anadolstudio.view.basetextinput.validator.validator.implementation

import com.anadolstudio.view.basetextinput.validator.validator.BaseValidator
import com.anadolstudio.view.basetextinput.validator.validator.ValidateResult

open class BasePredicateValidator(
        protected val predicate: (String) -> ValidateResult,
) : BaseValidator() {

    override fun validate(text: String): ValidateResult =
            predicate.invoke(text).also { result -> notifyAll(result) }
}
