package com.bonepeople.android.widget.util

import android.util.Log

/**
 * 日志输出工具类
 */
object AppLog {
    //日志TAG
    var tag = "AppLog"

    //日志输出开关，debug=true输出日志，默认为false
    var debug = false

    /**
     * 打印debug日志
     */
    fun debug(content: Any?) {
        if (debug) {
            Log.d(tag, content.toString())
        }
    }

    /**
     * 打印info日志
     */
    fun print(content: Any?) {
        if (debug) {
            Log.i(tag, content.toString())
        }
    }

    /**
     * 输出错误日志
     */
    fun error(message: Any?, throwable: Throwable? = null) {
        if (debug) {
            Log.e(tag, message.toString(), throwable)
        }
    }
}