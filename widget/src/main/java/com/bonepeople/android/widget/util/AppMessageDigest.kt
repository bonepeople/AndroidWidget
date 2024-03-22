package com.bonepeople.android.widget.util

import java.io.BufferedInputStream
import java.io.InputStream
import java.security.MessageDigest

/**
 * Utility class for generating message digests.
 */
@Suppress("MemberVisibilityCanBePrivate")
object AppMessageDigest {
    /**
     * Computes the MD5 hash of the data from the given InputStream and returns it as a string.
     * - Work in progress: Add support for terminating calculation prematurely.
     * @param inputStream The input stream containing data to hash.
     * @param blockSize The buffer size for reading the stream, useful for controlling the frequency of [onProgress] callbacks.
     * @param onProgress Callback to report progress, providing the current number of bytes processed.
     * @return The MD5 hash of the input data as a string.
     */
    fun md5(inputStream: InputStream, blockSize: Int = 1024, onProgress: ((Long) -> Unit)? = null): String {
        val messageDigest: MessageDigest = MessageDigest.getInstance("MD5")
        var md5 = ""
        readStream(inputStream, blockSize, messageDigest::update, {
            md5 = AppText.byteArrayToString(messageDigest.digest())
        }, onProgress)
        return md5
    }

    /**
     * Computes the MD5 hash of the given string.
     * @param content The string to hash.
     * @return The MD5 hash as a string.
     */
    fun md5(content: String): String = md5(content.byteInputStream())

    /**
     * Reads data from the input stream and applies the provided update and finalization logic.
     * @param inputStream The input stream to read from.
     * @param blockSize The size of the buffer for reading data in chunks.
     * @param onUpdate Function to process each read buffer chunk.
     * @param onFinal Optional function to execute final logic after all data is read.
     * @param onProgress Optional callback to track progress by reporting the cumulative number of bytes read.
     */
    private fun readStream(
        inputStream: InputStream,
        blockSize: Int,
        onUpdate: (buffer: ByteArray, offset: Int, length: Int) -> Unit,
        onFinal: (() -> Unit)? = null,
        onProgress: ((Long) -> Unit)? = null
    ) {
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