package com.bonepeople.android.widget.util

import android.util.Log
import com.bonepeople.android.widget.ApplicationHolder

/**
 * Logging utility class
 */
@Suppress("Unused", "MemberVisibilityCanBePrivate")
class AppLog(val tag: String) {
    /**
     * Logging toggle
     */
    var enable = true

    /**
     * Flag to include the current calling method's location in log messages
     */
    var showStackInfo = false

    /**
     * Stack offset for retrieving the calling stack trace; needs manual adjustment as required
     */
    var stackOffset = 5

    fun verbose(message: Any?, throwable: Throwable? = null) {
        if (AppLog.enable && enable) {
            Log.v(tag, addStackInfoIfNeed(message), throwable)
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
        /**
         * Default global logging instance
         */
        val defaultLog: AppLog get() = tag("AppLog")

        /**
         * Global logging toggle
         */
        var enable = ApplicationHolder.debug

        /**
         * Collection of AppLog instances
         */
        val instances = HashMap<String, AppLog>()

        /**
         * Retrieve an instance with the specified tag
         * + Instances are stored in [instances] for reuse
         */
        fun tag(tag: String): AppLog {
            return instances.getOrPut(tag) { AppLog(tag) }
        }
    }
}