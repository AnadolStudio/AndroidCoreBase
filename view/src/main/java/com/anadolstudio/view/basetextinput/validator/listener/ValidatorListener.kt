package com.anadolstudio.view.basetextinput.validator.listener

import com.anadolstudio.view.basetextinput.validator.validator.ValidateResult

interface ValidatorListener {

    fun onValidate(result: ValidateResult)

}
