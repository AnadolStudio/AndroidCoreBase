package com.anadolstudio.core.activity

import com.anadolstudio.core.livedata.SingleError
import com.anadolstudio.core.livedata.SingleMessage

// SingleMessage

class SingleMessageToast(text: String) : SingleMessage(text)

class SingleMessageSnack(text: String) : SingleMessage(text)

open class SingleMessageDialog(text: String) : SingleMessage(text) {

    class Information(text: String) : SingleMessageDialog(text)

    class Choice(text: String) : SingleMessageDialog(text)
}

// SingleError

class SingleErrorSnack(error: Throwable) : SingleError(error)

class SingleErrorDialog(error: Throwable) : SingleError(error)
