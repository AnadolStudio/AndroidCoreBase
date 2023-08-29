package com.anadolstudio.core.util.common_extention

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.Settings
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

fun Context.startAppSettingsActivity() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }

    startActivity(intent)
}

fun Context.hasPermission(permission: String): Boolean = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

fun Context.hasAllPermissions(permissions: Array<String>): Boolean = permissions.all(this::hasPermission)

fun Context.hasAnyPermissions(permissions: Array<String>): Boolean = permissions.any(this::hasPermission)

fun Context.getCompatDrawable(@DrawableRes id: Int): Drawable? = ContextCompat.getDrawable(this, id)
