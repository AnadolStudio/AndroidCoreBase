package com.anadolstudio.core.view.basetextinput.validator.validator

import com.anadolstudio.core.view.basetextinput.validator.listener.ValidatorListener

abstract class BaseValidator : Validator {

    protected var listeners: MutableList<ValidatorListener>? = null

    override fun addListener(listener: ValidatorListener) {
        listeners = listeners ?: mutableListOf()

        listeners?.add(listener)
    }

    override fun removeListener(listener: ValidatorListener) {
        listeners?.remove(listener)

        if (listeners.isNullOrEmpty()) {
            listeners = null
        }
    }

    protected fun notifyAll(result: ValidateResult) = listeners?.forEach { listener ->
        listener.onValidate(result)
    }

}
