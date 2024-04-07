package com.anadolstudio.utils.data_source.media

import org.joda.time.DateTime

data class Image(
    val path: String,
    val date: DateTime,
    val folder: String?,
    val format: String?,
)
