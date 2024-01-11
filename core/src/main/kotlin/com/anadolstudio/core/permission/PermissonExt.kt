package com.anadolstudio.core.permission

import android.Manifest
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.fragment.app.Fragment

val READ_MEDIA_PERMISSION: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    Manifest.permission.READ_MEDIA_IMAGES
} else {
    Manifest.permission.READ_EXTERNAL_STORAGE
}

fun Fragment.registerPermissionListRequest(
        onAllGranted: () -> Unit,
        onAnyDenied: () -> Unit,
        onAnyNotAskAgain: () -> Unit = {},
): ActivityResultLauncher<Array<String>> = registerForActivityResult(
        RequestMultiplePermissions()
) { permissionMap ->
    when {
        permissionMap.values.all { isGranted -> isGranted } -> onAllGranted.invoke()
        permissionMap.any { (permission, _) -> notAskAgain(permission) } -> onAnyNotAskAgain.invoke()
        permissionMap.values.any { isGranted -> !isGranted } -> onAnyDenied.invoke()
    }
}

private fun Fragment.notAskAgain(permission: String): Boolean = !shouldShowRequestPermissionRationale(permission)

