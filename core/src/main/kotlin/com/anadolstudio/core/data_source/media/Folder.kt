package com.anadolstudio.core.data_source.media

data class Folder(
        val name: String,
        val thumbPath: String,
        val value: String? = name,
        val imageCount: Int = 0
)
