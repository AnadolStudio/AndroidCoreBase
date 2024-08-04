package com.anadolstudio.utils.util.data_time

import android.os.Parcelable
import com.anadolstudio.utils.util.extentions.tryOrNull
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

val Long.hours: Int get() = (this / 3600000).toInt()
val Long.minutes: Int get() = ((this - hours * 3600000) / 60000).toInt()
val Long.seconds: Int get() = ((this - hours * 3600000 - minutes * 60000) / 1000).toInt()

fun String.safeParseDateTime(): DateTime? = tryOrNull { DateTime.parse(this) }

@Parcelize
data class Time(
        val hours: Int,
        val minutes: Int,
        val seconds: Int,
) : Parcelable {
    constructor(millis: Long) : this(
            hours = millis.hours,
            minutes = millis.minutes,
            seconds = millis.seconds,
    )

    @IgnoredOnParcel
    val hoursString: String = hours.toTimeNumber()
    @IgnoredOnParcel
    val minutesString: String = minutes.toTimeNumber()
    @IgnoredOnParcel
    val secondsString: String = seconds.toTimeNumber()

    @IgnoredOnParcel
    val totalMinutes: Int = hours * 60 + minutes + seconds / 60

}

private fun Int.toTimeNumber(): String = if (this < 10) "0$this" else "$this"

