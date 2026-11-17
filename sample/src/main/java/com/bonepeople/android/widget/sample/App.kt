package com.bonepeople.android.widget.sample

import android.app.Application
import com.bonepeople.android.widget.CoroutinesHolder
import com.bonepeople.android.widget.sample.global.LogUtil
import com.bonepeople.android.widget.util.event.AppEvent
import com.bonepeople.android.widget.util.event.LibraryLogEvent

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppEvent.register(CoroutinesHolder.default) { event ->
            if (event is LibraryLogEvent) LogUtil.printLibrary(event)
        }
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            LogUtil.app.error("UncaughtException", e)
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }
}