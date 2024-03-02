package com.anadolstudio.ui.activity

import android.app.Activity

fun Activity.requestPermission(permission: String, requestCode: Int) = requestPermissions(arrayOf(permission), requestCode)

fun Activity.requestPermission(permissions: Array<String>, requestCode: Int) = requestPermissions(permissions, requestCode)

