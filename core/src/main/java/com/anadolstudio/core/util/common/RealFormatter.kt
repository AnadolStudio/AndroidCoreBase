package com.anadolstudio.core.util.common

import com.google.android.material.slider.LabelFormatter
import kotlin.math.roundToInt

object RealFormatter : LabelFormatter {

    override fun getFormattedValue(value: Float) = value.roundToInt().toString()

}
