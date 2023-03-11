package com.bonepeople.android.widget.util

import android.util.Log
import com.bonepeople.android.widget.ApplicationHolder

/**
 * 日志输出工具类
 */
@Suppress("UNUSED")
object AppLog {
    /**
     * 默认日志TAG
     */
    var tag = "AppLog"

    /**
     * 日志输出开关
     */
    var enable = ApplicationHolder.debug

    fun verbose(message: Any?, throwable: Throwable? = null) {
        if (enable) {
            Log.v(tag, message.toString(), throwable)
        }
    }

    fun debug(message: Any?, throwable: Throwable? = null) {
        if (enable) {
            Log.d(tag, message.toString(), throwable)
        }
    }

    fun info(message: Any?, throwable: Throwable? = null) {
        if (enable) {
            Log.i(tag, message.toString(), throwable)
        }
    }

    fun warn(message: Any?, throwable: Throwable? = null) {
        if (enable) {
            Log.w(tag, message.toString(), throwable)
        }
    }

    fun error(message: Any?, throwable: Throwable? = null) {
        if (enable) {
            Log.e(tag, message.toString(), throwable)
        }
    }
}