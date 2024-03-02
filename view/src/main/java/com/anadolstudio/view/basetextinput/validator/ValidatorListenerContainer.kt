package com.anadolstudio.view.basetextinput.validator

import com.anadolstudio.view.basetextinput.validator.listener.ValidatorListener
import com.anadolstudio.view.basetextinput.validator.validator.ValidateResult
import com.anadolstudio.view.common.BaseListenersContainer

class ValidatorListenerContainer : BaseListenersContainer<ValidatorListener>(), ValidatorListener {

    override fun onValidate(result: ValidateResult) =
            listeners.forEach { validatorListener -> validatorListener.onValidate(result) }

}
