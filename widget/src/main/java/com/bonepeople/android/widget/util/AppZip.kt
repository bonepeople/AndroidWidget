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
 * 文件压缩工具类
 */
@Suppress("UNUSED")
object AppZip {
    /**
     * 压缩单个文件
     * @param fileName 待压缩文件的文件名
     * @param inputStream 输入流
     * @param outputStream 输出流
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
     * 压缩多个文件或文件夹
     * @param outputStream 输出流
     * @param files 待压缩文件或文件夹
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
     * 解压缩文件至目录
     * @param inputStream 压缩包数据流
     * @param parentFile 目标目录地址
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