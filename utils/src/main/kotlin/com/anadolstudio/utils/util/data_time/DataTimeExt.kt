package com.anadolstudio.utils.util.data_time

import android.os.Parcelable
import com.anadolstudio.utils.util.extentions.tryOrNull
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import org.joda.time.DateTime

val Long.toHours: Int get() = (this / 3_600_000).toInt()
val Long.toMinutes: Int get() = (this / 60_000).toInt()
val Long.toSeconds: Int get() = (this / 1_000).toInt()
val Long.remainingMinutes: Int get() = this.toMinutes % 60
val Long.remainingSeconds: Int get() = this.toSeconds % 60

fun String.safeParseDateTime(): DateTime? = tryOrNull { DateTime.parse(this) }

@Parcelize
data class Time(
        val hours: Int,
        val minutes: Int,
        val seconds: Int,
) : Parcelable {
    constructor(millis: Long) : this(
            hours = millis.toHours,
            minutes = millis.remainingMinutes,
            seconds = millis.remainingSeconds,
    )

    @IgnoredOnParcel
    val hoursString: String = hours.toTimeNumber()
    @IgnoredOnParcel
    val minutesString: String = minutes.toTimeNumber()
    @IgnoredOnParcel
    val secondsString: String = seconds.toTimeNumber()

    @IgnoredOnParcel
    val totalMinutes: Int = hours * 60 + minutes + seconds / 60

    @IgnoredOnParcel
    val totalSeconds: Int = hours * 3600 + minutes * 60 + seconds

}

private fun Int.toTimeNumber(): String = if (this < 10) "0$this" else "$this"

