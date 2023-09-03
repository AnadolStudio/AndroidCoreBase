package com.anadolstudio.core.view.common

import android.text.Editable
import android.text.TextWatcher

class TextWatcherContainer : BaseListenersContainer<TextWatcher>(), TextWatcher {

    override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) =
            listeners.forEach { textWatcher -> textWatcher.beforeTextChanged(text, start, count, after) }

    override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) =
            listeners.forEach { textWatcher -> textWatcher.onTextChanged(text, start, before, count) }

    override fun afterTextChanged(text: Editable?) =
            listeners.forEach { textWatcher -> textWatcher.afterTextChanged(text) }

}
