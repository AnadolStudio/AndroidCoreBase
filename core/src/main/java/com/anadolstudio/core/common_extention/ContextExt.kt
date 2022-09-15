package com.anadolstudio.core.common_extention

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

fun Context.startAppSettingsActivity() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    }

    startActivity(intent)
}
