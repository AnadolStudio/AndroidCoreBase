package com.anadolstudio.core.presentation.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment

fun <F : Fragment> F.withArgs(action: Bundle.() -> Unit): F = this.apply { arguments = Bundle().also(action) }

fun Fragment.requestPermission(permission: String, requestCode: Int) =
        requireActivity().requestPermissions(arrayOf(permission), requestCode)

fun Fragment.requestPermission(permissions: Array<String>, requestCode: Int) =
        requireActivity().requestPermissions(permissions, requestCode)

fun Fragment.hasPermission(permission: String): Boolean =
        requireActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

fun Fragment.hasPermission(permissions: Array<String>): Boolean = permissions.all(this::hasPermission)
