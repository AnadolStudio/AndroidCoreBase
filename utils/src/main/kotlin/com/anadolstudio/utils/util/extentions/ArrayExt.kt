package com.anadolstudio.utils.util.extentions

fun <T> Array<T>.nullIfEmpty(): Array<T>? = ifEmpty { null }
