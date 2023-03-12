package com.bonepeople.android.widget.simple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bonepeople.android.widget.CoroutinesHolder
import com.bonepeople.android.widget.activity.result.launch
import com.bonepeople.android.widget.simple.databinding.ActivityMainBinding
import com.bonepeople.android.widget.util.AppLog
import com.bonepeople.android.widget.util.AppTime
import com.bonepeople.android.widget.util.AppView.singleClick
import com.bonepeople.android.widget.view.SimpleLoadingDialog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private val views: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val loading by lazy { SimpleLoadingDialog(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(views.root)
        initView()
        initData()
    }

    private fun initView() {
        views.buttonTest.singleClick { startTest() }
        views.buttonContract.singleClick { Intent(this, ContractActivity::class.java).launch() }
    }

    private fun initData() {

    }

    private fun startTest() {
        CoroutinesHolder.main.launch {
            loading.show()
            runCatching {
                measureTimeMillis {
                    AppLog.debug("start test")
                    test()
                }.let {
                    AppLog.debug("used ${AppTime.getTimeString(it)}")
                }
            }.getOrElse {
                AppLog.error("exception@MainActivity.test", it)
            }
            loading.dismiss()
        }
    }

    private suspend fun test() {
        //...
        delay(1000)
    }
}