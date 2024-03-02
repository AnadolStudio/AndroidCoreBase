package com.anadolstudio.utils.util.extentions

fun Int.toColorHex(): String = String.format("#%06X", 0xFFFFFF and this)

fun Long.nullIfNotExist(): Long? = if (this == -1L) null else this

fun Int.nullIfNotExist(): Int? = if (this == -1) null else this
