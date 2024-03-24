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
 * Utility class for time conversion
 */
@Suppress("UNUSED")
object AppTime {
    private val calendar: Calendar by lazy { Calendar.getInstance() }

    /**
     * Formats a 13-digit timestamp into a string of the form "2021/1/1 12:33:33".
     * @param withMillis Includes milliseconds in the returned string, default is **false**.
     * @param timeZone The timezone, default is the current system timezone. Use [TimeZone.getTimeZone], e.g., `TimeZone.getTimeZone("GMT+2")` for GMT+2.
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
     * Formats a timestamp into a string using a specified pattern.
     * @param timestamp A 13-digit timestamp.
     * @param pattern Formatting template, e.g., "yyyy-MM-dd HH:mm:ss.SSS".
     * @param timeZone The timezone, default is the current system timezone. Use [TimeZone.getTimeZone], e.g., `TimeZone.getTimeZone("GMT+2")` for GMT+2.
     * @return The formatted string.
     * @throws DateTimeException If the time format is invalid.
     * @throws IllegalArgumentException If the pattern is invalid.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatTime(timestamp: Long, pattern: String = "yyyy-MM-dd HH:mm:ss", timeZone: TimeZone = TimeZone.getDefault()): String {
        return Instant.ofEpochMilli(timestamp).atZone(timeZone.toZoneId()).format(DateTimeFormatter.ofPattern(pattern))
    }

    /**
     * Formats a timestamp into a string of the form "102:04:59.999".
     * @param fullTimeString Pads hours or minutes with **00** when absent, default is **false**.
     * @param withMillis Includes milliseconds in the output, default is **true**.
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