package com.bonepeople.android.widget

import android.app.Application
import android.content.Context
import android.content.pm.PackageInfo
import androidx.core.content.pm.PackageInfoCompat
import androidx.startup.Initializer
import com.bonepeople.android.widget.activity.result.IntentCallback

/**
 * Application状态保存器
 */
object ApplicationHolder {
    /**
     * 当前App的Application实例
     */
    lateinit var instance: Application

    /**
     * 当前App的PackageInfo
     */
    val packageInfo: PackageInfo by lazy { instance.packageManager.getPackageInfo(getPackageName(), 0) }

    /**
     * 获取当前App的包名
     */
    fun getPackageName(): String {
        return instance.packageName
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
     * App的初始化逻辑
     */
    class AppInitializer : Initializer<ApplicationHolder> {
        override fun create(context: Context): ApplicationHolder {
            if (context is Application) {
                instance = context
                instance.registerActivityLifecycleCallbacks(ActivityHolder)
                instance.registerActivityLifecycleCallbacks(IntentCallback.Register)
            }
            return ApplicationHolder
        }

        override fun dependencies(): List<Class<out Initializer<*>>> {
            return emptyList()
        }
    }
}