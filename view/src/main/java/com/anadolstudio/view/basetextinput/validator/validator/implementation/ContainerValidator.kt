package com.anadolstudio.view.basetextinput.validator.validator.implementation

import com.anadolstudio.view.basetextinput.validator.ValidatorUnit
import com.anadolstudio.view.basetextinput.validator.validator.BaseValidator
import com.anadolstudio.view.basetextinput.validator.validator.Validatable
import com.anadolstudio.view.basetextinput.validator.validator.ValidateResult

open class ContainerValidator(
        private val validatorUnits: List<ValidatorUnit>,
) : BaseValidator() {

    constructor(vararg validatorUnits: ValidatorUnit) : this(validatorUnits.toList())

    @Suppress("detekt.LabeledExpression", "detekt.NestedBlockDepth")
    override fun validate(text: String): ValidateResult {
        var currentText = text

        val validateResult: ValidateResult = run breaking@{
            validatorUnits.forEach { unit ->
                when (unit) {
                    is com.anadolstudio.view.basetextinput.validator.mapper.ValidateMapper -> currentText = unit.map(currentText)
                    is Validatable -> unit.validate(currentText).let { result ->
                        when (result) {
                            is ValidateResult.Error -> return@breaking ValidateResult.Error(text, result.errorMessageId)
                            is ValidateResult.Success -> currentText = result.data
                        }
                    }
                }
            }

            return@breaking ValidateResult.Success(text)
        }

        notifyAll(validateResult)

        return validateResult
    }
}
