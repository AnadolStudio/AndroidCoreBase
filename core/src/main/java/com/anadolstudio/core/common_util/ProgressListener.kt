package com.anadolstudio.core.common_util

interface ProgressListener<T> {

    fun onProgress(progressData: ProgressData<T>)

    fun onProgress(progress: Int, data: T?) = onProgress(
            ProgressData(
                    progress = progress,
                    data = data
            )
    )
}

data class ProgressData<T>(
        val progress: Int,
        val data: T? = null
)
