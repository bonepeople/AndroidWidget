package com.bonepeople.android.widget.sample.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.bonepeople.android.widget.CoroutinesHolder
import com.bonepeople.android.widget.sample.LogUtil
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MyService : Service() {
    private val name = "MyService(${hashCode().toString(36)})"
    private var serviceJob: Job? = null

    override fun onCreate() {
        super.onCreate()
        LogUtil.test.verbose("$name-onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogUtil.test.verbose("$name-onStartCommand flags = $flags, startId = $startId")
        val running = serviceJob?.isActive ?: false
        if (!running) runService()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        LogUtil.test.verbose("$name-onBind")
        return null
    }

    override fun onUnbind(intent: Intent): Boolean {
        LogUtil.test.verbose("$name-onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent) {
        LogUtil.test.verbose("$name-onRebind")
        super.onRebind(intent)
    }

    override fun onDestroy() {
        LogUtil.test.verbose("$name-onDestroy")
        serviceJob?.cancel()
        super.onDestroy()
    }

    private fun runService() {
        serviceJob = CoroutinesHolder.default.launch {
            repeat(10) {
                delay(1000)
                LogUtil.test.debug("$name is running (${it + 1})")
            }
            stopSelf()
        }
    }
}