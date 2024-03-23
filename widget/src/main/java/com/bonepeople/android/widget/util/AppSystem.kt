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
 * System-related utility class for retrieving system parameters and information.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
@SuppressLint("PrivateApi", "DiscouragedPrivateApi", "InternalInsetResource")
object AppSystem {
    val batteryManager: BatteryManager by lazy { ApplicationHolder.app.getSystemService(Context.BATTERY_SERVICE) as BatteryManager }
    val androidId: String by lazy { Settings.System.getString(ApplicationHolder.app.contentResolver, Settings.Secure.ANDROID_ID) }

    /**
     * Retrieves the current battery percentage (0..100).
     */
    val batteryPercent: Int
        get() = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

    /**
     * Retrieves the current process name.
     * Returns an empty string if the process name cannot be determined.
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
     * Adds a listener for battery level changes.
     * @param context The context to register the listener.
     * @param onChange Callback invoked with the battery percentage when it changes.
     * @return The [BroadcastReceiver] used for listening, which must be unregistered when no longer needed.
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
     * Retrieves all active IPv4 addresses on the device.
     * @return A list of IPv4 addresses.
     * @throws SocketException if there is an error accessing the network interfaces.
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
     * Retrieves all active IPv6 addresses on the device.
     * @return A list of IPv6 addresses.
     * @throws SocketException if there is an error accessing the network interfaces.
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
     * Retrieves the broadcast address for the current network.
     * @return The broadcast address, or null if unavailable.
     * @throws SocketException if there is an error accessing the network interfaces.
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
     * Retrieves the status bar height.
     * @return The height of the status bar in pixels, or 0 if not available.
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
     * Retrieves the navigation bar height.
     * @return The height of the navigation bar in pixels, or 0 if not available.
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
     * Retrieves the screen width in pixels.
     */
    fun getScreenWidth(): Int {
        val point = Point()
        val manager = ApplicationHolder.app.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getRealSize(point)
        return point.x
    }

    /**
     * Retrieves the screen height in pixels.
     */
    fun getScreenHeight(): Int {
        val point = Point()
        val manager = ApplicationHolder.app.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        manager.defaultDisplay.getRealSize(point)
        return point.y
    }
}