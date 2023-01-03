package com.anadolstudio.core.event

import com.anadolstudio.core.livedata.SingleError
import com.anadolstudio.core.livedata.SingleMessage

// SingleMessage

open class SingleMessageToast(text: String) : SingleMessage(text) {

    class Long(text: String) : SingleMessageToast(text)
    class Short(text: String) : SingleMessageToast(text)
}

open class SingleMessageSnack(text: String) : SingleMessage(text) {

    class Long(text: String) : SingleMessageSnack(text)
    class Short(text: String) : SingleMessageSnack(text)
    class Indefinite(text: String) : SingleMessageSnack(text)
}

open class SingleMessageDialog(text: String) : SingleMessage(text) {

    class Information(text: String) : SingleMessageDialog(text)
    class Choice(text: String) : SingleMessageDialog(text)
}

// SingleError

class SingleErrorToast(error: Throwable) : SingleError(error)

class SingleErrorSnack(error: Throwable) : SingleError(error)

class SingleErrorDialog(error: Throwable) : SingleError(error)
