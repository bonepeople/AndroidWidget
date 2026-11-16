package com.bonepeople.android.widget.util.event

/**
 * Runtime log from a dependency library, broadcast through [AppEvent].
 *
 * App developers can collect these events to see how the libraries are running.
 * Every field is fixed on the calling thread before the event is queued.
 * The text and call site are not read again from a subscriber coroutine.
 * Subscribers should treat it as read-only and should not mutate [throwable].
 *
 * [priority] uses [android.util.Log] levels such as [android.util.Log.DEBUG].
 * [message] is only the text passed to the log call.
 * [timestamp] is [System.currentTimeMillis] at the call.
 * [threadName] is the name of the thread that called the log API.
 * [className], [methodName], and [lineNumber] are the caller location, read on the calling thread.
 * A missing stack frame uses [UNKNOWN_CLASS], [UNKNOWN_METHOD], and 0.
 *
 * [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/LibraryLog)
 */
data class LibraryLogEvent(
    val priority: Int,
    val tag: String,
    val message: String,
    val throwable: Throwable?,
    val timestamp: Long,
    val threadName: String,
    val className: String = UNKNOWN_CLASS,
    val methodName: String = UNKNOWN_METHOD,
    val lineNumber: Int = 0
) : AppEventData {
    companion object {
        const val UNKNOWN_CLASS = "UnknownClass"
        const val UNKNOWN_METHOD = "UnknownMethod"
    }
}