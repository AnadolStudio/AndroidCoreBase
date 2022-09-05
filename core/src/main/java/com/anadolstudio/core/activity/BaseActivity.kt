package com.anadolstudio.core.activity

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    abstract fun showError(error: Throwable)

    abstract fun showMessage(message: MessageType)

    abstract fun showErrorDialog()

    abstract fun showInformationDialog()

}
