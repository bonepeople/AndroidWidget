package com.bonepeople.android.widget

import android.app.Application
import android.content.Context
import androidx.startup.Initializer

object ApplicationHolder {
    lateinit var instance: Application

    class AppInitializer : Initializer<ApplicationHolder> {
        override fun create(context: Context): ApplicationHolder {
            if (context is Application) {
                instance = context
            }
            return ApplicationHolder
        }

        override fun dependencies(): List<Class<out Initializer<*>>> {
            return emptyList()
        }
    }
}