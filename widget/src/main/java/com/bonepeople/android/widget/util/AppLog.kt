package com.bonepeople.android.widget.util

import android.util.Log
import com.bonepeople.android.widget.ApplicationHolder

/**
 * 日志输出工具类
 */
object AppLog {
    /**
     * 默认日志TAG
     */
    var tag = "AppLog"

    /**
     * 日志输出开关
     */
    var enable = ApplicationHolder.debug

    /**
     * 打印debug日志
     */
    fun debug(content: Any?) {
        if (enable) {
            Log.d(tag, content.toString())
        }
    }

    /**
     * 打印info日志
     */
    fun print(content: Any?) {
        if (enable) {
            Log.i(tag, content.toString())
        }
    }

    /**
     * 输出错误日志
     */
    fun error(message: Any?, throwable: Throwable? = null) {
        if (enable) {
            Log.e(tag, message.toString(), throwable)
        }
    }
}