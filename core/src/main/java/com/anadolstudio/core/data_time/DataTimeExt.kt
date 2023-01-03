package com.anadolstudio.core.data_time

import com.anadolstudio.core.common_extention.tryOrNull
import org.joda.time.DateTime

fun String.safeParseDateTime(): DateTime? = tryOrNull { DateTime.parse(this) }

