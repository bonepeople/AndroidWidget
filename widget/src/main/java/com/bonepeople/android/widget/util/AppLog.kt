package com.bonepeople.android.widget.util

import android.util.Log
import com.bonepeople.android.widget.ApplicationHolder

/**
 * 日志输出工具类
 */
@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
class AppLog(val tag: String) {
    /**
     * 日志输出开关
     */
    var enable = true

    /**
     * 是否在日志信息中添加当前调用方法位置的信息
     */
    var showStackInfo = false

    /**
     * 在显示调用栈信息时获取的调用栈位置，需要根据不同情况手动调整
     */
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
        /**
         * 全局默认日志输出对象
         */
        val defaultLog: AppLog get() = tag("AppLog")

        /**
         * 全局日志输出开关
         */
        var enable = ApplicationHolder.debug

        /**
         * AppLog实例集合
         */
        val instances = HashMap<String, AppLog>()

        /**
         * 获取指定标签的实例
         * + 创建的实例会保存在[instances]中，供下次使用
         */
        fun tag(tag: String): AppLog {
            return instances.getOrPut(tag) { AppLog(tag) }
        }

        @Deprecated("推荐使用对象的方法输出日志", ReplaceWith("AppLog.defaultLog.verbose(message, throwable)", "com.bonepeople.android.widget.util.AppLog"))
        fun verbose(message: Any?, throwable: Throwable? = null) = defaultLog.verbose(message, throwable)

        @Deprecated("推荐使用对象的方法输出日志", ReplaceWith("AppLog.defaultLog.debug(message, throwable)", "com.bonepeople.android.widget.util.AppLog"))
        fun debug(message: Any?, throwable: Throwable? = null) = defaultLog.debug(message, throwable)

        @Deprecated("推荐使用对象的方法输出日志", ReplaceWith("AppLog.defaultLog.info(message, throwable)", "com.bonepeople.android.widget.util.AppLog"))
        fun info(message: Any?, throwable: Throwable? = null) = defaultLog.info(message, throwable)

        @Deprecated("推荐使用对象的方法输出日志", ReplaceWith("AppLog.defaultLog.warn(message, throwable)", "com.bonepeople.android.widget.util.AppLog"))
        fun warn(message: Any?, throwable: Throwable? = null) = defaultLog.warn(message, throwable)

        @Deprecated("推荐使用对象的方法输出日志", ReplaceWith("AppLog.defaultLog.error(message, throwable)", "com.bonepeople.android.widget.util.AppLog"))
        fun error(message: Any?, throwable: Throwable? = null) = defaultLog.error(message, throwable)
    }
}