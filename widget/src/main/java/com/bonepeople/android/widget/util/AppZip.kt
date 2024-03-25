package com.bonepeople.android.widget.util

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream

/**
 * Utility class for file compression and decompression
 */
@Suppress("UNUSED")
object AppZip {
    /**
     * Compresses a single file.
     * @param fileName The name of the file to be compressed
     * @param inputStream Input stream of the file
     * @param outputStream Output stream for the compressed data
     */
    fun zipFile(fileName: String, inputStream: InputStream, outputStream: OutputStream) {
        val output = ZipOutputStream(outputStream)
        val entry = ZipEntry(fileName)
        output.putNextEntry(entry)
        inputStream.copyTo(output)
        output.closeEntry()
        output.finish()
        output.flush()
    }

    /**
     * Compresses multiple files or directories.
     * @param outputStream Output stream for the compressed data
     * @param files Files or directories to be compressed
     */
    fun zipFiles(outputStream: OutputStream, vararg files: File) {
        val output = ZipOutputStream(outputStream)
        files.forEach { file ->
            zip(null, file, output)
        }
        output.finish()
        output.flush()
    }

    private fun zip(dir: String?, source: File, outputStream: ZipOutputStream) {
        val name = dir?.let { it + File.separatorChar + source.name } ?: source.name
        if (source.isDirectory) {
            source.listFiles().let { files ->
                if (files.isNullOrEmpty()) {
                    val entry = ZipEntry(name + File.separatorChar)
                    outputStream.putNextEntry(entry)
                    outputStream.closeEntry()
                } else {
                    files.forEach { file ->
                        zip(name, file, outputStream)
                    }
                }
            }
        } else {
            val entry = ZipEntry(name)
            outputStream.putNextEntry(entry)
            FileInputStream(source).copyTo(outputStream)
            outputStream.closeEntry()
        }
    }

    /**
     * Extracts a compressed file to a specified directory.
     * + Throws an exception when extracting files with non-UTF-8 encoding.
     * @param inputStream Input stream of the compressed file
     * @param parentFile Target directory for extraction
     */
    fun unzipFile(inputStream: InputStream, parentFile: File) {
        parentFile.mkdirs()
        val input = ZipInputStream(inputStream)
        var entry: ZipEntry? = input.nextEntry
        while (entry != null) {
            val file = File(parentFile, entry.name)
            if (entry.isDirectory) {
                file.mkdirs()
            } else {
                file.parentFile?.mkdirs()
                FileOutputStream(file).use { input.copyTo(it) }
            }
            entry = input.nextEntry
        }
    }
}