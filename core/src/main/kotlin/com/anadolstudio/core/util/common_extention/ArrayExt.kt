package com.anadolstudio.core.util.common_extention

fun <T> Array<T>.nullIfEmpty(): Array<T>? = ifEmpty { null }
