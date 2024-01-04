package com.anadolstudio.core.view.basetextinput.validator.validator.implementation

import androidx.annotation.StringRes
import com.anadolstudio.core.util.email.EmailUtil
import com.anadolstudio.core.util.email.EmailUtil.containsTwoDots

class EmailValidator(
        @StringRes errorMessage: Int,
) : SimpleValidator(
        condition = { email ->
            // TODO убрать containsTwoDots в регулярку
            EmailUtil.EMAIL_PATTERN_REGEX.matches(email) && !email.containsTwoDots()
        },
        errorMessage = errorMessage
)
