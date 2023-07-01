package com.anadolstudio.core.common_extention

fun <T> Array<T>.nullIfEmpty(): Array<T>? = ifEmpty { null }
