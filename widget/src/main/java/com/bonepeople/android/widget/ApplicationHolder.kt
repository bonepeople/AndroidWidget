package com.bonepeople.android.widget

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import androidx.core.content.pm.PackageInfoCompat
import androidx.startup.Initializer
import com.bonepeople.android.widget.activity.result.IntentLauncher

/**
 * Application State Holder
 *
 * Provides a centralized place to access and manage the application instance and related information.
 *
 * [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ApplicationHolder)
 */
@Suppress("Unused", "MemberVisibilityCanBePrivate")
object ApplicationHolder {
    private var application: Application? = null

    /**
     * The current application's [Application] instance.
     * Throws an [IllegalStateException] if not successfully initialized.
     */
    val app: Application
        get() = application ?: throw IllegalStateException("Initialization failed. Please call StartupHelper.initializeAll to initialize.")

    /**
     * The current application's [PackageInfo].
     */
    val packageInfo: PackageInfo by lazy { app.packageManager.getPackageInfo(getPackageName(), 0) }

    /**
     * Indicates whether the current application is in debug mode.
     */
    val debug: Boolean by lazy { app.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0 }

    /**
     * Retrieves the package name of the current application.
     */
    fun getPackageName(): String {
        return app.packageName
    }

    /**
     * Retrieves the version code of the current application.
     */
    fun getVersionCode(): Long {
        return PackageInfoCompat.getLongVersionCode(packageInfo)
    }

    /**
     * Retrieves the version name of the current application.
     */
    fun getVersionName(): String {
        return packageInfo.versionName
    }

    /**
     * Initialization logic for [ApplicationHolder].
     */
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