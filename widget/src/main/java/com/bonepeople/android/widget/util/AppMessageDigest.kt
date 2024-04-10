package com.bonepeople.android.widget.util

import java.io.BufferedInputStream
import java.io.InputStream
import java.security.MessageDigest

object AppMessageDigest {
    fun md5(inputStream: InputStream, blockSize: Int = 1024, onProgress: ((Long) -> Unit)? = null): String {
        val messageDigest: MessageDigest = MessageDigest.getInstance("MD5")
        var md5 = ""
        readStream(inputStream, blockSize, messageDigest::update, {
            md5 = AppText.byteArrayToString(messageDigest.digest())
        }, onProgress)
        return md5
    }

    fun md5(content: String): String = md5(content.byteInputStream())

    private fun readStream(inputStream: InputStream, blockSize: Int, onUpdate: (buffer: ByteArray, offset: Int, length: Int) -> Unit, onFinal: (() -> Unit)? = null, onProgress: ((Long) -> Unit)? = null) {
        inputStream.buffered().let { input: BufferedInputStream ->
            val buffer = ByteArray(blockSize)
            var length: Int
            var count = 0L
            while (input.read(buffer, 0, buffer.size).also { length = it } != -1) {
                onUpdate(buffer, 0, length)
                count += length
                onProgress?.invoke(count)
            }
            onFinal?.invoke()
        }
    }
}