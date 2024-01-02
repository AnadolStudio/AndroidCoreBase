package com.anadolstudio.core.util.data_time

import com.anadolstudio.core.util.common_extention.tryOrNull
import org.joda.time.DateTime

val Long.hours: Int get() = (this / 3600000).toInt()
val Long.minutes: Int get() = ((this - hours * 3600000) / 60000).toInt()
val Long.seconds: Int get() = ((this - hours * 3600000 - minutes * 60000) / 1000).toInt()

fun String.safeParseDateTime(): DateTime? = tryOrNull { DateTime.parse(this) }

data class Time(
    val hours: Int,
    val minutes: Int,
    val seconds: Int,
) {
    constructor(millis: Long) : this(
        hours = millis.hours,
        minutes = millis.minutes,
        seconds = millis.seconds,
    )

    val hoursString: String = hours.toTimeNumber()
    val minutesString: String = minutes.toTimeNumber()
    val secondsString: String = seconds.toTimeNumber()
}

private fun Int.toTimeNumber(): String = if (this < 10) "0$this" else "$this"

