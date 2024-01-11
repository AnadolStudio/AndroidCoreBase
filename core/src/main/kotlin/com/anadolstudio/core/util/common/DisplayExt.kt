package com.anadolstudio.core.util.common

import android.content.res.Resources

fun Int.dpToPx() = (this * Resources.getSystem().displayMetrics.density).toInt()
fun Int.pxToDp() = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.spToPx() = (this * Resources.getSystem().displayMetrics.scaledDensity).toInt()
fun Int.pxToSp() = (this / Resources.getSystem().displayMetrics.scaledDensity).toInt()

fun Float.spToPx() = (this * Resources.getSystem().displayMetrics.scaledDensity)
fun Float.pxToSp() = (this / Resources.getSystem().displayMetrics.scaledDensity)

fun Float.dpToPx() = (this * Resources.getSystem().displayMetrics.density)
fun Float.pxToDp() = (this / Resources.getSystem().displayMetrics.density)
