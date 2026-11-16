package com.bonepeople.android.widget.util

import android.util.Log
import com.bonepeople.android.widget.util.event.AppEvent
import com.bonepeople.android.widget.util.event.LibraryLogEvent
import java.util.concurrent.ConcurrentHashMap

/**
 * Logging entry for a dependency library.
 *
 * A library records its runtime here so an app developer can see what the library is doing.
 * Every call builds a [LibraryLogEvent] on the calling thread and queues it with [AppEvent.postAsync].
 * This library only publishes the event. The app subscribes and decides how to present it.
 *
 * [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/LibraryLog)
 */
@Suppress("Unused", "MemberVisibilityCanBePrivate")
class LibraryLog(val tag: String) {
    /**
     * Index into [Thread.getStackTrace] for the caller location.
     * Every log call reads this frame on the calling thread.
     *
     * Default is 4 for a direct call:
     * platform frame, [Thread.getStackTrace], send, level method, caller.
     * Adjust it when the log call is wrapped.
     */
    var stackOffset = 5

    fun verbose(message: Any?, throwable: Throwable? = null) = send(Log.VERBOSE, message, throwable)
    fun debug(message: Any?, throwable: Throwable? = null) = send(Log.DEBUG, message, throwable)
    fun info(message: Any?, throwable: Throwable? = null) = send(Log.INFO, message, throwable)
    fun warn(message: Any?, throwable: Throwable? = null) = send(Log.WARN, message, throwable)
    fun error(message: Any?, throwable: Throwable? = null) = send(Log.ERROR, message, throwable)

    private fun send(priority: Int, message: Any?, throwable: Throwable?) {
        val thread = Thread.currentThread()
        val timestamp = System.currentTimeMillis()
        val stack = thread.stackTrace.getOrNull(stackOffset)
        val className = stack?.className ?: LibraryLogEvent.UNKNOWN_CLASS
        val methodName = stack?.methodName ?: LibraryLogEvent.UNKNOWN_METHOD
        val lineNumber = stack?.lineNumber ?: 0
        AppEvent.postAsync(LibraryLogEvent(priority, tag, "$message", throwable, timestamp, thread.name, className, methodName, lineNumber))
    }

    companion object {
        /**
         * Default shared logger, tagged `LibraryLog`.
         */
        val defaultLog: LibraryLog get() = tag("LibraryLog")

        private val instances = ConcurrentHashMap<String, LibraryLog>()

        /**
         * Returns the shared logger for [tag].
         * The same tag always returns the same instance.
         */
        fun tag(tag: String): LibraryLog {
            return instances.getOrPut(tag) { LibraryLog(tag) }
        }
    }
}