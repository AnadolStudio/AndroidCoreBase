package com.anadolstudio.core.util.common_extention

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.core.os.BundleCompat.getParcelable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.anadolstudio.core.R

fun Fragment.getDrawable(@DrawableRes id: Int): Drawable? = requireContext().getCompatDrawable(id)

fun Fragment.setFragmentResult(requestKey: String, result: Parcelable) = setFragmentResult(
        requestKey,
        bundleOf(getString(R.string.data) to result)
)

fun Fragment.setFragmentResult(requestKey: String, result: Any) = setFragmentResult(
        requestKey,
        bundleOf(getString(R.string.data) to result)
)

fun Fragment.setFragmentResult(requestKey: String) = setFragmentResult(requestKey, bundleOf())

inline fun <reified Data : Parcelable> Fragment.getParcelable(bundle: Bundle): Data? {
    return getParcelable(bundle, getString(R.string.data), Data::class.java)
}
