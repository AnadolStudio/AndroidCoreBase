package com.anadolstudio.view.basetextinput.delegates.validator

import com.anadolstudio.view.basetextinput.validator.listener.ValidatorListener
import com.anadolstudio.view.basetextinput.validator.listener.implementation.SimpleValidatorListener
import com.anadolstudio.view.basetextinput.validator.validator.ValidateResult
import com.anadolstudio.view.basetextinput.validator.validator.Validator
import com.anadolstudio.view.basetextinput.validator.ValidatorListenerContainer

abstract class BaseInputValidatorDelegate : InputValidatorDelegate {

    protected var currentValidator: Validator? = null
    protected var validatorListeners = ValidatorListenerContainer()
    protected var needValidateFlag: Boolean = true
    protected var validateFlag: Int = InputValidatorDelegate.FLAG_NONE

    override fun manualValidate() {
        currentValidator?.let { validator -> validate(validator = validator, isManualValidate = true) }
    }

    override fun validate() {
        currentValidator?.let(this::validate)
    }

    override fun validate(validator: Validator) = validate(validator = validator, isManualValidate = false)

    abstract fun validate(validator: Validator, isManualValidate: Boolean)

    override fun getValidateModeFlag(): Int = validateFlag

    override fun setValidatorMode(flagMode: Int) {
        validateFlag = flagMode
    }

    override fun hasNeedToValidateFlag(): Boolean = needValidateFlag

    override fun setNeedToValidateFlag(isNeed: Boolean) {
        needValidateFlag = isNeed
    }

    override fun setValidator(validator: Validator?) {
        currentValidator = validator?.apply { addListener(validatorListeners) }
    }

    override fun addValidateListener(onSuccess: ((String) -> Unit)?, onFailure: ((String, Int) -> Unit)?) = addValidateListener(
            SimpleValidatorListener { result ->
                when (result) {
                    is ValidateResult.Success -> onSuccess?.invoke(result.data)
                    is ValidateResult.Error -> onFailure?.invoke(result.data, result.errorMessageId)
                }
            }
    )

    override fun addValidateListener(listener: ValidatorListener) {
        validatorListeners.add(listener)
    }

}
