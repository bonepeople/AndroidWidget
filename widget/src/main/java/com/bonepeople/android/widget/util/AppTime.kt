package com.bonepeople.android.widget.util

import java.lang.StringBuilder
import java.util.Calendar

/**
 * 时间转换工具类
 */
object AppTime {
    private val calendar: Calendar by lazy { Calendar.getInstance() }

    /**
     * 将13位的时间戳格式化为"2021/1/1 12:33:33"形式的字符串
     * @param withMillis 返回的字符串中包含毫秒数
     */
    fun getDateTimeString(timestamp: Long, withMillis: Boolean = false): String {
        calendar.timeInMillis = timestamp
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        val hour = calendar[Calendar.HOUR_OF_DAY]
        val minute = formatTimeNumber(calendar[Calendar.MINUTE].toLong(), 2)
        val second = formatTimeNumber(calendar[Calendar.SECOND].toLong(), 2)
        val millisecond = formatTimeNumber(calendar[Calendar.MILLISECOND].toLong(), 3)

        return if (withMillis) {
            "$year/$month/$day $hour:$minute:$second.$millisecond"
        } else {
            "$year/$month/$day $hour:$minute:$second"
        }
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

    fun formatTimeNumber(number: Long, length: Int): String {
        val fillSize = length - number.toString().length
        val result = StringBuilder()
        for (i in 1..fillSize) {
            result.append('0')
        }
        result.append(number)
        return result.toString()
    }
}