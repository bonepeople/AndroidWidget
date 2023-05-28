package com.bonepeople.android.widget.util

import androidx.annotation.CheckResult
import androidx.annotation.IntRange
import java.lang.StringBuilder
import java.math.BigDecimal

/**
 * 字符串处理工具类
 */
@Suppress("UNUSED")
object AppText {

    /**
     * 以指定字符串填充原始数据的前部剩余位置
     * + 示例1："3" + "000" => "003"
     * + 示例2："3691" + "000" => "3691"
     * @param source 原始数据
     * @param formatStr 填充字符串
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
     * 以指定字符串填充原始数据的后部剩余位置
     * + 示例1："3" + "###" => "3##"
     * + 示例2："3691" + "###" => "3691"
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
     * 为数字添加千分位
     * + 示例：12345.9 => 12,345.9
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
     * 为字符串自动换行
     * + 每段文字会单独换行处理，不影响段落分隔
     * + 示例：
     *     ```
     *     wrapString("1234567890123456789",7)
     *     1234567
     *     8901234
     *     56789
     *     ```
     * @param source 待处理数据
     * @param length 单行最大长度，默认为76
     * @param separator 换行符，默认为"\n"，特殊情况下可指定为其他的换行符
     */
    @CheckResult
    fun wrapString(source: CharSequence, @IntRange(from = 1) length: Int = 76, separator: CharSequence = "\n"): String {
        require(length > 0) { "单行长度必须大于0" }
        return source.lines().joinToString(separator) { paragraph ->
            paragraph.chunked(length).joinToString(separator)
        }
    }
}