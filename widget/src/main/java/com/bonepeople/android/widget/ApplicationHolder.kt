package com.bonepeople.android.widget

import android.app.Application
import android.content.Context
import androidx.core.content.pm.PackageInfoCompat
import androidx.startup.Initializer

object ApplicationHolder {
    lateinit var instance: Application
    val packageInfo by lazy { instance.packageManager.getPackageInfo(getPackageName(), 0) }

    fun getPackageName(): String {
        return instance.packageName
    }

    fun getVersionCode(): Long {
        return PackageInfoCompat.getLongVersionCode(packageInfo)
    }

    fun getVersionName(): String {
        return packageInfo.versionName
    }

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