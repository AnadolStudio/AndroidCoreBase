package com.anadolstudio.utils.util.files

import android.os.Environment
import java.io.File
import java.util.UUID

object FileUtils {

    const val CAMERA_DIRECTORY = "Camera"

    fun generateFileName(ext: String? = null) = UUID.randomUUID().toString() + (ext ?: "")

    fun generateFileName(fileExtension: FileExtension) = UUID.randomUUID().toString() + fileExtension.getExtension()

    fun getExternalStoragePublicDirectory(folderName: String): String {
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path

        return "${path}${File.separator}${folderName}"
    }
}
