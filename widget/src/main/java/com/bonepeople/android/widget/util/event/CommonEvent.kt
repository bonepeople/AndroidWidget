package com.bonepeople.android.widget.util.event

/**
 * Generic event with [name] and [data].
 *
 * @param name Event identifier defined by the host app.
 * @param data Event payload; defaults to [Unit] for signal-only events.
 */
data class CommonEvent(val name: String, val data: Any = Unit) : AppEventData