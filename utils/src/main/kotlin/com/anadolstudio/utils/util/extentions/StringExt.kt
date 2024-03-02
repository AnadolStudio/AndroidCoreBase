package com.anadolstudio.utils.util.extentions

fun String.ifNotEmpty(action: () -> String): String = if (isNotEmpty()) plus(action.invoke()) else this

fun String.nullIfEmpty(): String? = ifEmpty { null }

