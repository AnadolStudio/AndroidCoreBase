package com.anadolstudio.core.presentation.event

import com.anadolstudio.core.viewmodel.livedata.SingleError
import com.anadolstudio.core.viewmodel.livedata.SingleMessage

/**
 * SingleMessage
 */
sealed class SingleMessageToast(text: String) : SingleMessage(text) {
    class Long(text: String) : SingleMessageToast(text)
    class Short(text: String) : SingleMessageToast(text)
}

sealed class SingleMessageSnack(text: String) : SingleMessage(text) {
    class Long(text: String) : SingleMessageSnack(text)
    class Short(text: String) : SingleMessageSnack(text)
    class Indefinite(text: String) : SingleMessageSnack(text)
}

/**
 * SingleError
 */
sealed class SingleErrorToast(error: Throwable) : SingleError(error) {
    class Long(error: Throwable) : SingleErrorToast(error)
    class Short(error: Throwable) : SingleErrorToast(error)
}

sealed class SingleErrorSnack(error: Throwable) : SingleError(error) {
    class Long(error: Throwable) : SingleErrorSnack(error)
    class Short(error: Throwable) : SingleErrorSnack(error)
}
