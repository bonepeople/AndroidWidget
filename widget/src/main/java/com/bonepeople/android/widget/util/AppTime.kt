package com.bonepeople.android.widget.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.IllegalArgumentException
import java.lang.StringBuilder
import java.time.DateTimeException
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.TimeZone

/**
 * 时间转换工具类
 */
@Suppress("UNUSED")
object AppTime {
    private val calendar: Calendar by lazy { Calendar.getInstance() }

    /**
     * 将13位的时间戳格式化为"2021/1/1 12:33:33"形式的字符串
     * @param withMillis 返回的字符串中包含毫秒数，默认为**false**
     * @param timeZone 时区，默认为系统当前时区，可通过[TimeZone.getTimeZone]获取，`TimeZone.getTimeZone("GMT+2")`表示东二区
     */
    fun getDateTimeString(timestamp: Long, withMillis: Boolean = false, timeZone: TimeZone = TimeZone.getDefault()): String {
        calendar.timeZone = timeZone
        calendar.timeInMillis = timestamp
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        val hour = AppText.completeStart(calendar[Calendar.HOUR_OF_DAY], "00")
        val minute = AppText.completeStart(calendar[Calendar.MINUTE], "00")
        val second = AppText.completeStart(calendar[Calendar.SECOND], "00")
        val millisecond = AppText.completeStart(calendar[Calendar.MILLISECOND], "000")

        return if (withMillis) {
            "$year/$month/$day $hour:$minute:$second.$millisecond"
        } else {
            "$year/$month/$day $hour:$minute:$second"
        }
    }

    /**
     * 按照指定格式将时间戳格式化为字符串
     * @param timestamp 13位时间戳
     * @param pattern 格式化模版，例如"yyyy-MM-dd HH:mm:ss.SSS"
     * @param timeZone 时区，默认为系统当前时区，可通过[TimeZone.getTimeZone]获取，`TimeZone.getTimeZone("GMT+2")`表示东二区
     * @return 格式化后的字符串
     * @throws DateTimeException 时间格式化异常
     * @throws IllegalArgumentException 模版不合法
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(timestamp: Long, pattern: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getDefault()): String {
        return Instant.ofEpochMilli(timestamp).atZone(timeZone.toZoneId()).format(DateTimeFormatter.ofPattern(pattern))
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

        val milliStr = AppText.completeStart(milliseconds % 1000, "000")
        val secondStr = AppText.completeStart(seconds % 60, "00")
        val minuteStr = AppText.completeStart(minutes % 60, "00")
        val hourStr = AppText.completeStart(hours, "00")

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
}