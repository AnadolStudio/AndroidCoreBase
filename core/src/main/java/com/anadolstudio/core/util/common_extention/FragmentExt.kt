package com.anadolstudio.core.util.common_extention

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment

fun Fragment.getDrawable(@DrawableRes id: Int):Drawable? = requireContext().getCompatDrawable(id)
