package com.bonepeople.android.widget.util

import androidx.annotation.CheckResult
import androidx.annotation.IntRange
import java.lang.StringBuilder
import java.math.BigDecimal
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

/**
 * Utility class for string manipulation
 */
@Suppress("Unused")
object AppText {
    /**
     * Fills the beginning of the original data with the specified string until the specified length is reached.
     * + Example 1: "3" + "000" => "003"
     * + Example 2: "3691" + "000" => "3691"
     * @param source The original data
     * @param formatStr The string used for padding
     */
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

    /**
     * Fills the end of the original data with the specified string until the specified length is reached.
     * + Example 1: "3" + "###" => "3##"
     * + Example 2: "3691" + "###" => "3691"
     */
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

    /**
     * Adds thousand separators to a number.
     * + Example: 12345.9 => 12,345.9
     */
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

    /**
     * Automatically wraps text into multiple lines.
     * + Each section of text is processed separately without affecting paragraph separation.
     * + Example:
     *     ```
     *     wrapString("1234567890123456789", 7)
     *     1234567
     *     8901234
     *     56789
     *     ```
     * @param source The data to process
     * @param length The maximum length per line, default is 76
     * @param separator The line break character, default is "\n", can be customized if needed
     */
    @CheckResult
    fun wrapString(source: CharSequence, @IntRange(from = 1) length: Int = 76, separator: CharSequence = "\n"): String {
        require(length > 0) { "Line length must be greater than 0" }
        return source.lines().joinToString(separator) { paragraph ->
            paragraph.chunked(length).joinToString(separator)
        }
    }

    /**
     * Converts a file size in bytes into a human-readable string using binary (base-1024) units.
     *
     * Examples:
     * - 0 bytes   -> "0 B"
     * - 1024      -> "1 KiB"
     * - 1048576   -> "1 MiB"
     *
     * Thanks: https://stackoverflow.com/questions/66019926/android-text-format-formatter-formatfilesize-returns-wrong-format
     *
     * @param size The file size in bytes.
     * @return A string representing the human-readable file size, using units from B to YiB.
     */
    fun formatFileSize(size: Long): String {
        if (size <= 0) return "0 B"
        val units = arrayOf("B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB", "ZiB", "YiB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / 1024.0.pow(digitGroups.toDouble())).toString() + " " + units[digitGroups]
    }

    /**
     * Converts a byte array into a hexadecimal string.
     */
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

    /**
     * Converts a hexadecimal string into a byte array.
     */
    @CheckResult
    fun stringToByteArray(data: String): ByteArray {
        return data.chunked(2).map {
            it.toInt(16).toUByte().toByte()
        }.toByteArray()
    }
}