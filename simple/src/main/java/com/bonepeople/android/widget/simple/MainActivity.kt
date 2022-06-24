package com.bonepeople.android.widget.simple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bonepeople.android.widget.activity.result.launch
import com.bonepeople.android.widget.simple.databinding.ActivityMainBinding
import com.bonepeople.android.widget.util.AppLog
import com.bonepeople.android.widget.util.singleClick

class MainActivity : AppCompatActivity() {
    private val views: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        initView()
        initData(savedInstanceState)
    }

    private fun initView() {
        views.buttonTest.singleClick { test() }
        views.buttonContract.singleClick { Intent(this, ContractActivity::class.java).launch() }
    }

    private fun initData(savedInstanceState: Bundle?) {

    }

    private fun test() {
        kotlin.runCatching {
            AppLog.print("test")
        }.getOrElse {
            AppLog.error("test", it)
        }
    }
}