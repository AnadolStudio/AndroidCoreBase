package com.anadolstudio.core.util.night_mode

import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatDelegate

fun getCurrentNightMode(resources: Resources): Int {
    return when (val systemNightMode = AppCompatDelegate.getDefaultNightMode()) {
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> getNightModeFromSystem(resources)
        else -> systemNightMode
    }
}

private fun getNightModeFromSystem(resources: Resources): Int {
    return when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_NO -> AppCompatDelegate.MODE_NIGHT_NO
        else -> AppCompatDelegate.MODE_NIGHT_YES
    }
}
