package com.anadolstudio.core.util.common_extention

fun Int.toColorHex(): String = String.format("#%06X", 0xFFFFFF and this)
