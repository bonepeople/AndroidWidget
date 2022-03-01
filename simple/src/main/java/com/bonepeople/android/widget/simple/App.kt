package com.bonepeople.android.widget.simple

import android.app.Application
import com.bonepeople.android.widget.util.AppLog

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppLog.enable = BuildConfig.DEBUG
    }
}