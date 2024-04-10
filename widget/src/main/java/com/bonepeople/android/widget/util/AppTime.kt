package com.bonepeople.android.widget.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.lang.StringBuilder
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.TimeZone

@Suppress("UNUSED")
object AppTime {
    private val calendar: Calendar by lazy { Calendar.getInstance() }

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(timestamp: Long, pattern: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getDefault()): String {
        return Instant.ofEpochMilli(timestamp).atZone(timeZone.toZoneId()).format(DateTimeFormatter.ofPattern(pattern))
    }

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