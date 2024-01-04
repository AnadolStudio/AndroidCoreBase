package com.anadolstudio.core.view.basetextinput.validator.listener.implementation

import com.anadolstudio.core.view.basetextinput.validator.listener.ValidatorListener
import com.anadolstudio.core.view.basetextinput.validator.validator.ValidateResult

open class SuccessOrNullValidatorListener(private val onValidate: (String?) -> Unit) : ValidatorListener {

    override fun onValidate(result: ValidateResult) = onValidate.invoke((result as? ValidateResult.Success)?.data)

}
