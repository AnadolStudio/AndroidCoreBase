package com.anadolstudio.core.util.data_time

import com.anadolstudio.core.util.common_extention.tryOrNull
import org.joda.time.DateTime

fun String.safeParseDateTime(): DateTime? = tryOrNull { DateTime.parse(this) }

