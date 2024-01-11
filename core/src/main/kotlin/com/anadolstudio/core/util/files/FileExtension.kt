package com.anadolstudio.core.util.files

import java.util.Locale

enum class FileExtension {
    JPG, JPEG, GIF, BMP, PNG, PDF, DOC, DOCX, TXT, XLS, XLSX, RTF;

    fun getExtension(): String = ".${name.lowercase(Locale.ROOT)}"

}

