package com.bonepeople.android.widget

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import androidx.core.content.pm.PackageInfoCompat
import androidx.startup.Initializer
import com.bonepeople.android.widget.activity.result.IntentLauncher

/**
 * Application状态保存器
 */
@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object ApplicationHolder {
    private var application: Application? = null

    /**
     * 当前App的Application实例
     */
    @Deprecated("使用app代替")
    val instance: Application
        get() = application ?: throw IllegalStateException("未成功初始化，请调用StartupHelper.initializeAll方法进行初始化")

    /**
     * 当前App的Application实例
     */
    val app: Application
        get() = application ?: throw IllegalStateException("未成功初始化，请调用StartupHelper.initializeAll方法进行初始化")

    /**
     * 当前App的PackageInfo
     */
    val packageInfo: PackageInfo by lazy { app.packageManager.getPackageInfo(getPackageName(), 0) }

    /**
     * 当前App的debug标志
     */
    val debug: Boolean by lazy { app.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0 }

    /**
     * 获取当前App的包名
     */
    fun getPackageName(): String {
        return app.packageName
    }

    /**
     * 获取当前App的版本号
     */
    fun getVersionCode(): Long {
        return PackageInfoCompat.getLongVersionCode(packageInfo)
    }

    /**
     * 获取当前App的版本名称
     */
    fun getVersionName(): String {
        return packageInfo.versionName
    }

    /**
     * ApplicationHolder的初始化逻辑
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