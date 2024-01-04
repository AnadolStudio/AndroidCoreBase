package com.anadolstudio.core.view.basetextinput.validator.listener

import com.anadolstudio.core.view.basetextinput.validator.validator.ValidateResult

interface ValidatorListener {

    fun onValidate(result: ValidateResult)

}
