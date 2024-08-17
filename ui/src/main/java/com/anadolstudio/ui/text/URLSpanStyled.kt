package com.anadolstudio.ui.text

import android.text.TextPaint
import android.text.style.URLSpan

class URLSpanStyled(
    url: String,
    private val isUnderlineText: Boolean = false,
    private val isFakeBoldText: Boolean = true
) : URLSpan(url) {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = isUnderlineText
        ds.isFakeBoldText = isFakeBoldText
    }

}
