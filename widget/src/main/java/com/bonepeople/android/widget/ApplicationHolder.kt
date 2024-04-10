package com.bonepeople.android.widget

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import androidx.core.content.pm.PackageInfoCompat
import androidx.startup.Initializer
import com.bonepeople.android.widget.activity.result.IntentLauncher

@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object ApplicationHolder {
    private var application: Application? = null

    val app: Application
        get() = application ?: throw IllegalStateException("please call StartupHelper.initializeAll first")

    val packageInfo: PackageInfo by lazy { app.packageManager.getPackageInfo(getPackageName(), 0) }

    val debug: Boolean by lazy { app.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0 }

    fun getPackageName(): String {
        return app.packageName
    }

    fun getVersionCode(): Long {
        return PackageInfoCompat.getLongVersionCode(packageInfo)
    }

    fun getVersionName(): String {
        return packageInfo.versionName
    }

    class StartUp : Initializer<ApplicationHolder> {
        override fun create(context: Context): ApplicationHolder {
            if (context is Application) {
                application = context
                app.registerActivityLifecycleCallbacks(ActivityHolder)
                app.registerActivityLifecycleCallbacks(IntentLauncher.Registry)
            }
            return ApplicationHolder
        }

        override fun dependencies(): List<Class<out Initializer<*>>> {
            return emptyList()
        }
    }
}