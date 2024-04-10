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

@Suppress("unused", "MemberVisibilityCanBePrivate")
@SuppressLint("PrivateApi", "DiscouragedPrivateApi", "InternalInsetResource")
object AppSystem {
    val batteryManager: BatteryManager by lazy { ApplicationHolder.app.getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
    val androidId: String by lazy { Settings.System.getString(ApplicationHolder.app.contentResolver, Settings.Secure.ANDROID_ID) }

    val batteryPercent: Int
        get() = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

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

    fun getStatusBarHeight(): Int {
        val resource = ApplicationHolder.app.resources
        val resourceId = resource.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resource.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    fun getNavigationBarHeight(): Int {
        val resource = ApplicationHolder.app.resources
        val resourceId = resource.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            resource.getDimensionPixelSize(resourceId)
        } else {
            0
        }
    }

    fun getScreenWidth(): Int {
        val point = Point()
        val manager = ApplicationHolder.app.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getRealSize(point)
        return point.x
    }

    fun getScreenHeight(): Int {
        val point = Point()
        val manager = ApplicationHolder.app.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getRealSize(point)
        return point.y
    }
}