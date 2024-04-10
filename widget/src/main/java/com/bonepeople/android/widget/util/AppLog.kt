package com.bonepeople.android.widget.util

import android.util.Log
import com.bonepeople.android.widget.ApplicationHolder

@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
class AppLog(val tag: String) {
    var enable = true

    var showStackInfo = false

    var stackOffset = 5

    fun verbose(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.v(tag, message.toString(), throwable)
        }
    }

    fun debug(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.d(tag, addStackInfoIfNeed(message), throwable)
        }
    }

    fun info(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.i(tag, addStackInfoIfNeed(message), throwable)
        }
    }

    fun warn(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.w(tag, addStackInfoIfNeed(message), throwable)
        }
    }

    fun error(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.e(tag, addStackInfoIfNeed(message), throwable)
        }
    }

    private fun addStackInfoIfNeed(message: Any?): String {
        return if (showStackInfo) {
            val stack = Thread.currentThread().stackTrace.getOrNull(stackOffset)
            val className = stack?.className?.split(".")?.last() ?: "UnknownClass"
            val methodName = stack?.methodName ?: "UnknownMethod"
            val lineNumber = stack?.lineNumber ?: 0
            "$message @ $className.$methodName:$lineNumber"
        } else {
            message.toString()
        }
    }

    companion object {
        val defaultLog: AppLog get() = tag("AppLog")

        var enable = ApplicationHolder.debug

        val instances = HashMap<String, AppLog>()

        fun tag(tag: String): AppLog {
            return instances.getOrPut(tag) { AppLog(tag) }
        }
    }
}