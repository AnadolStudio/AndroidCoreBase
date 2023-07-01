package com.anadolstudio.core.activity

import android.app.Activity
import android.content.pm.PackageManager

fun Activity.requestPermission(permission: String, requestCode: Int) = requestPermissions(arrayOf(permission), requestCode)

fun Activity.requestPermission(permissions: Array<String>, requestCode: Int) = requestPermissions(permissions, requestCode)

fun Activity.hasPermission(permission: String): Boolean = checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

fun Activity.hasPermission(permissions: Array<String>): Boolean = permissions.all(this::hasPermission)
