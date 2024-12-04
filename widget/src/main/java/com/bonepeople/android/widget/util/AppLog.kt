package com.bonepeople.android.widget.util

import android.util.Log
import com.bonepeople.android.widget.ApplicationHolder

/**
 * Logging utility class
 *
 * [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppLog)
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

    /**
     * Flag to include the current calling thread's name in log messages
     */
    var showThreadInfo = false

    fun verbose(message: Any?, throwable: Throwable? = null) = printLog(message, throwable, Log::v)
    fun debug(message: Any?, throwable: Throwable? = null) = printLog(message, throwable, Log::d)
    fun info(message: Any?, throwable: Throwable? = null) = printLog(message, throwable, Log::i)
    fun warn(message: Any?, throwable: Throwable? = null) = printLog(message, throwable, Log::w)
    fun error(message: Any?, throwable: Throwable? = null) = printLog(message, throwable, Log::e)

    private fun assembleLog(message: Any?): String {
        val threadInfo = if (showThreadInfo) "[${Thread.currentThread().name}] " else ""
        val stackInfo = if (showStackInfo) {
            val stack = Thread.currentThread().stackTrace.getOrNull(stackOffset)
            val className = stack?.className?.split(".")?.last() ?: "UnknownClass"
            val methodName = stack?.methodName ?: "UnknownMethod"
            val lineNumber = stack?.lineNumber ?: 0
            " @ $className.$methodName:$lineNumber"
        } else ""
        return "$threadInfo$message$stackInfo"
    }

    private fun printLog(message: Any?, throwable: Throwable? = null, action: (tag: String, message: String, throwable: Throwable?) -> Int = Log::v) {
        if (AppLog.enable && enable) {
            val messageList = assembleLog(message).chunked(MAX_LOG_LENGTH)
            messageList.mapIndexed { index, subString ->
                action.invoke(tag, subString, if (index == messageList.lastIndex) throwable else null)
            }
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

        private const val MAX_LOG_LENGTH = 3000
    }
}