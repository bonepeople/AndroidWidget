package com.bonepeople.android.widget.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.bonepeople.android.widget.ApplicationHolder

/**
 * 系统相关参数的获取工具
 */
object AppSystem {
    val batteryManager: BatteryManager by lazy { ApplicationHolder.instance.getSystemService(AppCompatActivity.BATTERY_SERVICE) as BatteryManager }
    val androidId: String by lazy { Settings.System.getString(ApplicationHolder.instance.contentResolver, Settings.Secure.ANDROID_ID) }

    /**
     * 当前电池电量
     * + 取值范围1..100
     */
    val batteryPercent: Int = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

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
}