package com.bonepeople.android.widget.util

import java.lang.StringBuilder
import java.util.Calendar

/**
 * 时间转换工具类
 */
@Suppress("UNUSED")
object AppTime {
    private val calendar: Calendar by lazy { Calendar.getInstance() }

    /**
     * 将13位的时间戳格式化为"2021/1/1 12:33:33"形式的字符串
     * @param withMillis 返回的字符串中包含毫秒数，默认为**false**
     */
    fun getDateTimeString(timestamp: Long, withMillis: Boolean = false): String {
        calendar.timeInMillis = timestamp
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        val hour = fillString(calendar[Calendar.HOUR_OF_DAY], "00")
        val minute = fillString(calendar[Calendar.MINUTE], "00")
        val second = fillString(calendar[Calendar.SECOND], "00")
        val millisecond = fillString(calendar[Calendar.MILLISECOND], "000")

        return if (withMillis) {
            "$year/$month/$day $hour:$minute:$second.$millisecond"
        } else {
            "$year/$month/$day $hour:$minute:$second"
        }
    }

    /**
     * 将时间戳格式化为"102:04:59.999"形式的字符串
     * @param fullTimeString 时间不足小时或分钟的时候以**00**补全，默认为**false**
     * @param withMillis 显示毫秒数，默认为**true**
     */
    fun getTimeString(milliseconds: Long, fullTimeString: Boolean = false, withMillis: Boolean = true): String {
        val seconds = milliseconds / 1000
        val minutes = seconds / 60
        val hours = minutes / 60

        val milliStr = fillString(milliseconds % 1000, "000")
        val secondStr = fillString(seconds % 60, "00")
        val minuteStr = fillString(minutes % 60, "00")
        val hourStr = fillString(hours, "00")

        val stringBuilder = StringBuilder()
        if (hours != 0L || fullTimeString) {
            stringBuilder.append(hourStr)
            stringBuilder.append(':')
        }
        if (minutes != 0L || fullTimeString) {
            stringBuilder.append(minuteStr)
            stringBuilder.append(':')
        }
        if (stringBuilder.isEmpty()) {
            stringBuilder.append(seconds)
        } else {
            stringBuilder.append(secondStr)
        }
        if (withMillis) {
            stringBuilder.append('.')
            stringBuilder.append(milliStr)
        }

        return stringBuilder.toString()
    }

    fun fillString(number: Any, formatStr: String): String {
        val content = number.toString()
        val fillSize = formatStr.length - content.length
        val result = StringBuilder()
        repeat(fillSize) {
            result.append(formatStr[it])
        }
        result.append(content)
        return result.toString()
    }
}