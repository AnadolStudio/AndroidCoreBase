package com.anadolstudio.core.permission

import android.Manifest
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment

val READ_MEDIA_PERMISSION: String = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    Manifest.permission.READ_MEDIA_IMAGES
} else {
    Manifest.permission.READ_EXTERNAL_STORAGE
}

fun Fragment.registerPermissionRequest(
        permission: String,
        onGranted: () -> Unit,
        onDenied: () -> Unit,
        onDontAskAgain: () -> Unit = {},
): ActivityResultLauncher<String> = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
    when {
        isGranted -> onGranted.invoke()
        !shouldShowRequestPermissionRationale(permission) -> onDontAskAgain.invoke()
        else -> onDenied.invoke()
    }
}
