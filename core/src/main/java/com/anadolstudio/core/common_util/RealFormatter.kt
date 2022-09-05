package com.anadolstudio.core.common_util

import com.google.android.material.slider.LabelFormatter
import kotlin.math.roundToInt

object RealFormatter : LabelFormatter {

    override fun getFormattedValue(value: Float) = value.roundToInt().toString()

}
