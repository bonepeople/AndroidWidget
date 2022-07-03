package com.bonepeople.android.widget.util

import java.lang.StringBuilder
import java.text.DateFormat

/**
 * 时间转换工具类
 */
object AppTime {
    /**
     * 用于格式化时间戳的对象
     */
    private val dateFormatter: DateFormat by lazy {
        DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.MEDIUM)
    }

    /**
     * 将时间戳格式化为"2021/1/1 12:33:33"形式的字符串
     */
    fun getDateTimeString(milliseconds: Long): String {
        return dateFormatter.format(milliseconds)
    }

    /**
     * 将时间戳格式化为"102:04:59.999"形式的字符串
     */
    fun getTimeString(milliseconds: Long): String {
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        val milliStr = formatTimeNumber(milliseconds % 1000, 3)
        val secondStr = formatTimeNumber(seconds % 60, 2)
        val minuteStr = formatTimeNumber(minutes % 60, 2)

        return if (hours != 0L) {
            "$hours:$minuteStr:$secondStr.$milliStr"
        } else if (minutes != 0L) {
            "$minuteStr:$secondStr.$milliStr"
        } else if (seconds != 0L) {
            "${seconds % 60}.$milliStr"
        } else {
            "0.$milliStr"
        }
    }

    private fun formatTimeNumber(number: Long, length: Int): String {
        val fillSize = length - number.toString().length
        val result = StringBuilder()
        for (i in 1..fillSize) {
            result.append('0')
        }
        result.append(number)
        return result.toString()
    }
}