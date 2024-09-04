package com.bonepeople.android.widget.sample.service

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.activity.result.launch
import com.bonepeople.android.widget.sample.LogUtil
import com.bonepeople.android.widget.sample.databinding.ActivityServiceBinding
import com.bonepeople.android.widget.util.AppView.singleClick
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ServiceActivity : AppCompatActivity() {
    private val views: ActivityServiceBinding by lazy { ActivityServiceBinding.inflate(layoutInflater) }
    private val viewModel: ServiceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        initView()
    }

    private fun initView() {
        viewModel.serviceRunning.flowWithLifecycle(lifecycle).onEach {
            views.textViewStatus.text = if (it) "服务已启动" else "无服务"
        }.launchIn(lifecycleScope)
        views.buttonStart.singleClick { startService() }
        views.buttonStop.singleClick { stopService() }
    }

    private fun startService() {
        LogUtil.test.info("startService")
        val intent = Intent(this, MyService::class.java)
        startService(intent)
        viewModel.updateServiceStatus()
    }

    private fun stopService() {
        LogUtil.test.info("stopService")
        val intent = Intent(this, MyService::class.java)
        stopService(intent)
        viewModel.updateServiceStatus()
    }

    companion object {
        fun open() {
            ActivityHolder.getTopActivity()?.let {
                Intent(it, ServiceActivity::class.java).launch()
            }
        }
    }
}