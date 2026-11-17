package com.bonepeople.android.widget.sample.global

import android.util.Log
import com.bonepeople.android.widget.util.AppLog
import com.bonepeople.android.widget.util.event.LibraryLogEvent

object LogUtil {
    val app = AppLog("AppLog.App")
    val test = AppLog("AppLog.Test").apply {
        showThreadInfo = true
        showStackInfo = true
        stackOffset = 6
    }
    val library = AppLog("AppLog.LibraryLog")

    fun printLibrary(event: LibraryLogEvent) {
        val location = "${event.className.substringAfterLast('.')}.${event.methodName}:${event.lineNumber}"
        val message = "[${event.tag}] ${event.message} @ $location (${event.threadName})"
        when (event.priority) {
            Log.VERBOSE -> library.verbose(message, event.throwable)
            Log.DEBUG -> library.debug(message, event.throwable)
            Log.INFO -> library.info(message, event.throwable)
            Log.WARN -> library.warn(message, event.throwable)
            else -> library.error(message, event.throwable)
        }
    }
}