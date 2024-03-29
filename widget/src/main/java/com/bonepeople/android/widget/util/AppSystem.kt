package com.bonepeople.android.widget.util

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Point
import android.os.BatteryManager
import android.os.Build
import android.provider.Settings
import android.view.WindowManager
import androidx.annotation.RequiresPermission
import com.bonepeople.android.widget.ApplicationHolder
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.NetworkInterface
import java.net.SocketException

/**
 * 系统相关参数的获取工具
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
@SuppressLint("PrivateApi", "DiscouragedPrivateApi", "InternalInsetResource")
object AppSystem {
    val batteryManager: BatteryManager by lazy { ApplicationHolder.app.getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
    val androidId: String by lazy { Settings.System.getString(ApplicationHolder.app.contentResolver, Settings.Secure.ANDROID_ID) }

    /**
     * 当前电池电量
     * + 取值范围0..100
     */
    val batteryPercent: Int
        get() = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

    /**
     * 当前进程的名称
     * + 未成功获取进程名称的时候为空字符串
     */
    val processName: String by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Application.getProcessName()
        } else {
            kotlin.runCatching {
                Class.forName("android.app.ActivityThread").getDeclaredMethod("currentProcessName").invoke(null) as String
            }.getOrDefault("").ifBlank {
                kotlin.runCatching {
                    (ApplicationHolder.app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).let { manager ->
                        val pid: Int = android.os.Process.myPid()
                        manager.runningAppProcesses.firstOrNull { it.pid == pid }?.processName
                    }
                }.getOrNull() ?: ""
            }
        }
    }

    /**
     * 添加电量变化的监听函数
     * + 电量改变时会将计算得到的百分比数据回调到传入的函数中
     * + 方法会返回监听使用的[BroadcastReceiver]，用于在不需要监听的时候注销监听
     * + 注销时所使用的[Context]要与注册时一致
     */
    fun registerBatteryChanged(context: Context, onChange: (percent: Int) -> Unit): BroadcastReceiver {
        val receiver = object : BroadcastReceiver() {
            private var lastPercent = 0
            override fun onReceive(context: Context, intent: Intent) {
                val level = intent.getIntExtra("level", 0)
                val scale = intent.getIntExtra("scale", 0)
                val percent = kotlin.runCatching {
                    100 * level / scale
                }.getOrElse {
                    0
                }
                if (percent != lastPercent) {
                    lastPercent = percent
                    onChange(percent)
                }
            }
        }
        context.registerReceiver(receiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        return receiver
    }

    /**
     * 获取当前设备所有网络连接的IPv4地址
     * @return 返回当前已激活的IPv4地址列表
     * @throws SocketException
     */
    @RequiresPermission(android.Manifest.permission.INTERNET)
    fun getIpAddressV4(): List<String> {
        return NetworkInterface.getNetworkInterfaces()
            .toList()
            .filter { it.isUp && !it.isLoopback }
            .flatMap { networkInterface ->
                networkInterface.inetAddresses
                    .toList()
                    .filterIsInstance<Inet4Address>()
                    .map { it.hostAddress }
            }
    }

    /**
     * 获取当前设备所有网络连接的IPv6地址
     * @return 返回当前已激活的IPv6地址列表
     * @throws SocketException
     */
    @RequiresPermission(android.Manifest.permission.INTERNET)
    fun getIpAddressV6(): List<String> {
        return NetworkInterface.getNetworkInterfaces()
            .toList()
            .filter { it.isUp && !it.isLoopback }
            .flatMap { networkInterface ->
                networkInterface.inetAddresses
                    .toList()
                    .filterIsInstance<Inet6Address>()
                    .map { it.hostAddress }
            }
    }

    /**
     * 获取当前设备的IPv4广播地址
     * @return 返回广播地址，未获取到的情况下返回null
     * @throws SocketException
     */
    @RequiresPermission(android.Manifest.permission.INTERNET)
    fun getBroadcastAddress(): String? {
        NetworkInterface.getNetworkInterfaces().iterator().forEach { networkInterface: NetworkInterface ->
            if (!networkInterface.isUp || networkInterface.isLoopback) return@forEach
            networkInterface.interfaceAddresses.forEach { interfaceAddress ->
                interfaceAddress.broadcast?.hostAddress?.let { address ->
                    return address
                }
            }
        }
        return null
    }

    /**
     * 获取状态栏高度
     * @return 返回状态栏高度，单位为px，未获取到的情况下返回0
     */
    fun getStatusBarHeight(): Int {
        val resource = ApplicationHolder.app.resources
        val resourceId = resource.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resource.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    /**
     * 获取导航栏高度
     * @return 返回导航栏高度，单位为px，未获取到的情况下返回0
     */
    fun getNavigationBarHeight(): Int {
        val resource = ApplicationHolder.app.resources
        val resourceId = resource.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resource.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    /**
     * 获取屏幕宽度
     * @return 返回屏幕宽度，单位为px
     */
    fun getScreenWidth(): Int {
        val point = Point()
        val manager = ApplicationHolder.app.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getRealSize(point)
        return point.x
    }

    /**
     * 获取屏幕高度
     * @return 返回屏幕高度，单位为px
     */
    fun getScreenHeight(): Int {
        val point = Point()
        val manager = ApplicationHolder.app.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getRealSize(point)
        return point.y
    }
}