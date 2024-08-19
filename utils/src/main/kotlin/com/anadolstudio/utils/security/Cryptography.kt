package com.anadolstudio.utils.security

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import java.security.MessageDigest

class Cryptography {

    @Suppress("DEPRECATION")
    @SuppressLint("PackageManagerGetSignatures")
    fun getSignatureSha(context: Context): ByteArray {
        val signatures = with(context.packageManager) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                getPackageInfo(context.packageName, PackageManager.GET_SIGNING_CERTIFICATES)
                        .signingInfo
                        .apkContentsSigners
            } else {
                getPackageInfo(context.packageName, PackageManager.GET_SIGNATURES)
                        .signatures
            }
        }

        return sha256(signatures.first().toByteArray())
    }

    fun sha256(text: String) = sha256(text.toByteArray())

    fun sha256(byteArray: ByteArray): ByteArray {
        val digest = MessageDigest.getInstance("SHA-256")

        return with(digest) {
            update(byteArray)
            digest()
        }
    }

}
