package com.anadolstudio.ui.text

import android.graphics.Paint
import android.widget.TextView

fun <E : TextView> E.setUnderLine() {
    paintFlags = paintFlags or Paint.UNDERLINE_TEXT_FLAG
}
