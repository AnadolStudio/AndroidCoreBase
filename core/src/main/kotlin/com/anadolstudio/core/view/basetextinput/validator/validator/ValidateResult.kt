package com.anadolstudio.core.view.basetextinput.validator.validator

import androidx.annotation.StringRes

sealed class ValidateResult(val data: String) {

    class Success(data: String) : ValidateResult(data)

    class Error(data: String, @StringRes val errorMessageId: Int) : ValidateResult(data)

}
