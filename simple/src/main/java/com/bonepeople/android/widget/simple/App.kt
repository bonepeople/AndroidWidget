package com.bonepeople.android.widget.simple

import android.app.Application
import com.bonepeople.android.widget.util.AppLog

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            AppLog.error("UncaughtException", e)
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }
}