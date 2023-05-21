package com.bonepeople.android.widget.util

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
}