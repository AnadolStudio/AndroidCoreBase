package com.anadolstudio.core.util.common_extention

import kotlin.math.roundToInt
import kotlin.math.sqrt

object MathUtil {

    const val HALF_PI_ANGLE = 90

    fun radius(x: Int, y: Int): Int = radius(x.toFloat(), y.toFloat()).roundToInt()

    fun radius(x: Double, y: Double): Double = sqrt(x * x + y * y)

    fun radius(x: Float, y: Float): Float = sqrt(x * x + y * y)

    /**
     * Применение:
     * asin(x).toAngle()
     */
    fun Float.toAngle(): Float = this * HALF_PI_ANGLE

    /**
     * Применение:
     * asin(x).toAngle()
     */
    fun Int.toAngle(): Int = this * HALF_PI_ANGLE

    /**
     * Применение:
     * asin(x).toAngle()
     */
    fun Double.toAngle(): Double = this * HALF_PI_ANGLE

}
