package com.anadolstudio.utils.security

import android.content.Context
import android.util.Base64
import com.google.crypto.tink.Aead
import com.ironz.binaryprefs.encryption.ValueEncryption

class TinkValueEncryption(
        context: Context,
        cryptography: Cryptography,
        private val aead: Aead
) : ValueEncryption {

    private val signature = cryptography.getSignatureSha(context).takeLast(16).toByteArray()

    override fun encrypt(plaintext: ByteArray): ByteArray {
        val cipherText = aead.encrypt(plaintext, signature)

        return Base64.encode(cipherText, Base64.DEFAULT)
    }

    override fun decrypt(cipher: ByteArray): ByteArray {
        val cipherText = Base64.decode(cipher, Base64.DEFAULT)

        return aead.decrypt(cipherText, signature)
    }
}
