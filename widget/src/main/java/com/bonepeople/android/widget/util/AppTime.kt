package com.bonepeople.android.widget.util

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
    fun getDateTimeString(time: Long): String {
        return dateFormatter.format(time)
    }
}