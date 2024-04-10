package com.bonepeople.android.widget.util

import androidx.annotation.CheckResult
import androidx.annotation.IntRange
import java.lang.StringBuilder
import java.math.BigDecimal

@Suppress("UNUSED")
object AppText {

    @CheckResult
    fun completeStart(source: Any, formatStr: CharSequence): String {
        val content: String = source.toString()
        val fillSize: Int = formatStr.length - content.length
        val builder = StringBuilder()
        repeat(fillSize) {
            builder.append(formatStr[it])
        }
        builder.append(content)
        return builder.toString()
    }

    @CheckResult
    fun completeEnd(source: Any, formatStr: CharSequence): String {
        val content: String = source.toString()
        val fillSize: Int = formatStr.length - content.length
        val builder = StringBuilder(content)
        repeat(fillSize) {
            builder.append(formatStr[it + content.length])
        }
        return builder.toString()
    }

    @CheckResult
    fun formatNumber(number: Number): String {
        val numberString: String = BigDecimal(number.toString()).toString()
        val numbers: List<String> = numberString.split('.')
        val builder = StringBuilder()
        builder.append(numbers[0].reversed().chunked(3).joinToString(",").reversed())
        numbers.getOrNull(1)?.let {
            builder.append('.')
            builder.append(it)
        }
        return builder.toString()
    }

    @CheckResult
    fun wrapString(source: CharSequence, @IntRange(from = 1) length: Int = 76, separator: CharSequence = "\n"): String {
        require(length > 0) { "length must be greater than 0" }
        return source.lines().joinToString(separator) { paragraph ->
            paragraph.chunked(length).joinToString(separator)
        }
    }

    @CheckResult
    fun byteArrayToString(data: ByteArray): String {
        val buffer = StringBuffer()
        data.map { byte: Byte ->
            val hexStr: String = Integer.toHexString(byte.toInt() and 0xFF)
            if (hexStr.length < 2) {
                buffer.append(0)
            }
            buffer.append(hexStr)
        }
        return buffer.toString()
    }

    @CheckResult
    fun stringToByteArray(data: String): ByteArray {
        return data.chunked(2).map {
            it.toInt(16).toUByte().toByte()
        }.toByteArray()
    }
}