package com.anadolstudio.core.util.common_extention

fun String.ifNotEmpty(action: () -> String): String = if (isNotEmpty()) plus(action.invoke()) else this

fun String.nullIfEmpty(): String? = ifEmpty { null }

