package com.anadolstudio.core.util.share

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.anadolstudio.core.bitmap_util.BitmapDecoder
import java.io.File

interface SharedAction {

    fun runShareIntent(path: String, activity: AppCompatActivity, titleChooserDialog: String)

    open class Base(val appPackage: AppPackages?) : SharedAction {

        private companion object {
            const val IMAGE_TYPE = "image/*"
        }

        override fun runShareIntent(
                path: String,
                activity: AppCompatActivity,
                titleChooserDialog: String
        ) {
            val photoURI = if (path.contains(BitmapDecoder.Manager.CONTENT)) {
                Uri.parse(path)
            } else {
                FileProvider.getUriForFile(
                        activity,
                        activity.applicationContext.packageName + ".provider",
                        File(path)
                )
            }

            val share = Intent(Intent.ACTION_SEND).apply {
                type = IMAGE_TYPE
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                putExtra(Intent.EXTRA_STREAM, photoURI)
            }

            openAnotherApp(activity, share, appPackage, titleChooserDialog)
        }

        private fun openAnotherApp(
                activity: AppCompatActivity,
                share: Intent,
                appPackage: AppPackages?,
                title: String
        ) {
            when (appPackage == null) {
                true -> activity.startActivity(Intent.createChooser(share, title))
                false -> {
                    share.setPackage(appPackage.appPackage)

                    try {
                        activity.startActivity(share)
                    } catch (e: ActivityNotFoundException) {
                        openGooglePlay(appPackage.appPackage, activity)
                    }
                }
            }
        }

        protected fun openGooglePlay(namePackage: String, activity: AppCompatActivity) {
            try {
                activity.startActivityFromUri("market://details?id=$namePackage")
            } catch (ex: ActivityNotFoundException) {
                activity.startActivityFromUri("https://play.google.com/store/apps/details?id=$namePackage")
            }
        }

        private fun AppCompatActivity.startActivityFromUri(uri: String) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
        }
    }

    sealed class SharedItem(
            @DrawableRes val drawable: Int,
            appPackage: AppPackages?
    ) : Base(appPackage)
}
