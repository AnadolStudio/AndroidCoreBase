package com.anadolstudio.prosto.util

import android.graphics.Color

private const val SIGN = "#"
private const val COLOR_PATTERN = """[a-fA-F0-9]{6}"""

fun String?.parseColorOrDefault(defaultColor: String): Int = parseColorOrNull()
        ?: Color.parseColor(defaultColor)

fun String?.parseColorOrDefault(defaultColor: Int): Int = parseColorOrNull() ?: defaultColor

fun String?.parseColorOrNull(): Int? {
    if (this == null || !this.replace(SIGN, "").matches(Regex(COLOR_PATTERN))) return null

    return try {
        this.parseColor()
    } catch (e: IllegalArgumentException) {
        null
    }
}

@Throws(IllegalArgumentException::class)
fun String.parseColor(): Int {
    val correctString = this.replace(SIGN, "")

    return Color.parseColor("$SIGN$correctString")
}
