package com.bonepeople.android.widget.sample.service

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.activity.result.launch
import com.bonepeople.android.widget.sample.LogUtil
import com.bonepeople.android.widget.sample.databinding.ActivityServiceBinding
import com.bonepeople.android.widget.util.AppView.singleClick

class ServiceActivity : AppCompatActivity() {
    private val views: ActivityServiceBinding by lazy { ActivityServiceBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        initView()
    }

    private fun initView() {
        views.buttonStart.singleClick { startService() }
        views.buttonStop.singleClick { stopService() }
    }

    private fun startService() {
        LogUtil.test.info("startService")
        val intent = Intent(this, MyService::class.java)
        startService(intent)
    }

    private fun stopService() {
        LogUtil.test.info("stopService")
        val intent = Intent(this, MyService::class.java)
        stopService(intent)
    }

    companion object {
        fun open() {
            ActivityHolder.getTopActivity()?.let {
                Intent(it, ServiceActivity::class.java).launch()
            }
        }
    }
}