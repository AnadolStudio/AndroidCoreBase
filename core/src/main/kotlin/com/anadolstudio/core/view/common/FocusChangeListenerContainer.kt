package com.anadolstudio.core.view.common

import android.view.View

class FocusChangeListenerContainer : BaseListenersContainer<View.OnFocusChangeListener>(), View.OnFocusChangeListener {

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        for (i in listeners.indices) {
            listeners[i].onFocusChange(v, hasFocus)
        }
    }

    fun add(listener: (Boolean) -> Unit): Boolean = listeners.add(
            View.OnFocusChangeListener { _, hasFocus -> listener.invoke(hasFocus) }
    )

}
