package com.anadolstudio.utils.util.preferences

import com.ironz.binaryprefs.Preferences
import com.ironz.binaryprefs.PreferencesEditor

inline fun Preferences.modify(commit: Boolean = false, action: PreferencesEditor.() -> Unit) {
    with(edit()) {
        action(this)
        if (commit) commit() else apply()
    }
}
