package com.anadolstudio.core.view.basetextinput.validator

import com.anadolstudio.core.view.basetextinput.validator.listener.ValidatorListener
import com.anadolstudio.core.view.basetextinput.validator.validator.ValidateResult
import com.anadolstudio.core.view.common.BaseListenersContainer

class ValidatorListenerContainer : BaseListenersContainer<ValidatorListener>(), ValidatorListener {

    override fun onValidate(result: ValidateResult) =
            listeners.forEach { validatorListener -> validatorListener.onValidate(result) }

}
