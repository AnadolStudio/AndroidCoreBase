package com.anadolstudio.core.util.files

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException

object CameraUtil {

    fun Context.hasCamera(): Boolean = packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)

    fun openCamera(context: Context, file: File?, authority: String): CameraData {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val outputFileUri = createImageFileUri(context, file, authority)

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
        return CameraData(intent = takePictureIntent, uri = outputFileUri)
    }

    fun createImageFileUri(context: Context, file: File?, authority: String): Uri? {
        val saveFile = createImageFile(context, file)

        return FileProvider.getUriForFile(context, authority, saveFile)
    }

    @Throws(IOException::class)
    private fun createImageFile(context: Context, file: File?): File = File(
            file ?: context.filesDir,
            FileUtils.generateFileName(FileExtension.JPG)
    )

    data class CameraData(val intent: Intent, val uri: Uri?)
}
