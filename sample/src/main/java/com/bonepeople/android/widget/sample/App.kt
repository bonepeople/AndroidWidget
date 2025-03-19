package com.bonepeople.android.widget.sample

import android.app.Application
import com.bonepeople.android.widget.sample.global.LogUtil

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Thread.setDefaultUncaughtExceptionHandler { _, e ->
            LogUtil.app.error("UncaughtException", e)
            android.os.Process.killProcess(android.os.Process.myPid())
        }
    }
}