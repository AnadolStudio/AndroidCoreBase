package com.anadolstudio.utils.util.extentions

import android.graphics.Color
import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.core.math.MathUtils


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

fun getAverageColor(color1: Int, color2: Int): Int {
    val a1 = color1.alpha
    val r1 = color1.red
    val g1 = color1.green
    val b1 = color1.blue

    val a2 = color2.alpha
    val r2 = color2.red
    val g2 = color2.green
    val b2 = color2.blue

    return Color.argb(
            (a1 + a2) / 2,
            (r1 + r2) / 2,
            (g1 + g2) / 2,
            (b1 + b2) / 2,
    )
}

fun getColorByOffset(startColor: Int, endColor: Int, offset: Float): Int {
    val correctOffset = MathUtils.clamp(offset, 0F, 1F)

    val startA = startColor.alpha
    val startR = startColor.red
    val startG = startColor.green
    val startB = startColor.blue

    val deltaA = (endColor.alpha - startA)
    val deltaR = (endColor.red - startR)
    val deltaG = (endColor.green - startG)
    val deltaB = (endColor.blue - startB)

    return Color.argb(
            (startA + deltaA * correctOffset).toInt(),
            (startR + deltaR * correctOffset).toInt(),
            (startG + deltaG * correctOffset).toInt(),
            (startB + deltaB * correctOffset).toInt(),
    )
}
