package com.anadolstudio.core.activity

import androidx.appcompat.app.AppCompatActivity
import com.anadolstudio.core.livedata.SingleError
import com.anadolstudio.core.livedata.SingleEvent
import com.anadolstudio.core.livedata.SingleMessage

abstract class BaseActivity : AppCompatActivity() {

    abstract fun defaultErrorMessage(): String

    abstract fun showError(event: SingleError)

    abstract fun showMessage(event: SingleMessage)

    abstract fun showMessageDialog(event: SingleMessageDialog)

    abstract fun showErrorDialog(event: SingleErrorDialog)

    abstract fun handleEvent(event: SingleEvent)
}
