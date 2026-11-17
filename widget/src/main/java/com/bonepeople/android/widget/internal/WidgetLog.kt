package com.bonepeople.android.widget.internal

import com.bonepeople.android.widget.util.LibraryLog

/**
 * Runtime loggers for this library.
 *
 * Each call publishes a [com.bonepeople.android.widget.util.event.LibraryLogEvent].
 * The app subscribes and decides how to present it.
 */
internal object WidgetLog {
    val application = LibraryLog.Companion.tag("AndroidWidget.ApplicationHolder")
    val activity = LibraryLog.Companion.tag("AndroidWidget.ActivityHolder")
    val intent = LibraryLog.Companion.tag("AndroidWidget.IntentLauncher")
    val permission = LibraryLog.Companion.tag("AndroidWidget.AppPermission")
    val toast = LibraryLog.Companion.tag("AndroidWidget.AppToast")
    val keyboard = LibraryLog.Companion.tag("AndroidWidget.AppKeyboard")
    val stringResource = LibraryLog.Companion.tag("AndroidWidget.StringResource")
    val startup = LibraryLog.Companion.tag("AndroidWidget.StartupHelper")
    val coroutine = LibraryLog.Companion.tag("AndroidWidget.CoroutinesHolder")
    val data = LibraryLog.Companion.tag("AndroidWidget.AppData")
}