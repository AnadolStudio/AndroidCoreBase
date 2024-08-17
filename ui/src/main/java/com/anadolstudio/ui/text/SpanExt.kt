package com.anadolstudio.ui.text

import android.graphics.Typeface
import android.os.Build
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.style.CharacterStyle
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.text.util.Linkify
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.text.toSpannable
import androidx.core.text.util.LinkifyCompat.LinkifyMask
import com.anadolstudio.utils.util.extentions.getAllFirstAndLastIndexesByQuery
import java.util.regex.Pattern

private const val PHONE_NUMBER_PATTERN = """([+]?\d{1,3})?([( ]?\d{3}[ )]?)?[- ]?\d{2,3}[- ]?\d{2}[- ]?\d{2}"""

fun createClickableSpan(onClick: (View) -> Unit, @ColorInt color: Int, typeface: Typeface? = null): ClickableSpan {
    return object : ClickableSpan() {

        override fun onClick(widget: View) {
            onClick(widget)
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.color = color
            ds.isUnderlineText = false
            typeface?.let(ds::setTypeface)
        }
    }
}

fun String.createSpannableString(query: String, ignoreRegister: Boolean, span: CharacterStyle): SpannableString {
    val spannableString = SpannableString(this)
    if (query.isEmpty()) return spannableString

    val list = getAllFirstAndLastIndexesByQuery(query, ignoreRegister)

    list.forEach { (first, last) ->
        spannableString.setSpan(span, first, last, 0)
    }

    return spannableString
}

fun String.formatAsHtml(): Spanned? = when (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    true -> Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    false -> Html.fromHtml(this)
}

/*
Обрабатывается 2 варианта ссылок
1. Ссылки с html-тегами
2. url
При оборачивании текста в SpannableString getSpanEnd(span)/getSpanStart(span) не находят
в буфере нужное положение ссылки (в случае c html-тегами), поэтому добавлен второй цикл,
в котором поиск производится в customText
*/

fun String.formatLinkifyText(
        isUnderlineText: Boolean = false,
        isFakeBoldText: Boolean = true,
        @LinkifyMask mask: Int = Linkify.ALL
): SpannableString {
    val customText: Spanned = this.formatAsHtml() ?: "".toSpannable()
    val buffer = SpannableString(customText)

    Linkify.addLinks(buffer, mask)

    if (mask and Linkify.PHONE_NUMBERS != 0) {
        val phoneNumberPattern = Pattern.compile(PHONE_NUMBER_PATTERN)
        Linkify.addLinks(buffer, phoneNumberPattern, "tel:", null, Linkify.sPhoneNumberTransformFilter)
    }

    val urlSpans = buffer.getSpans(0, buffer.length, URLSpan::class.java)
    val textSpans = customText.getSpans(0, customText.length, URLSpan::class.java)

    urlSpans.forEach { span ->
        val end = buffer.getSpanEnd(span)
        val start = buffer.getSpanStart(span)
        buffer.setSpan(URLSpanStyled(span.url, isUnderlineText, isFakeBoldText), start, end, 0)
    }
    textSpans.forEach { span ->
        val end = customText.getSpanEnd(span)
        val start = customText.getSpanStart(span)
        buffer.setSpan(URLSpanStyled(span.url, isUnderlineText, isFakeBoldText), start, end, 0)
    }

    return buffer
}
