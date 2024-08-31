package com.bonepeople.android.widget.sample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bonepeople.android.widget.CoroutinesHolder
import com.bonepeople.android.widget.activity.result.launch
import com.bonepeople.android.widget.sample.databinding.ActivityMainBinding
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
    }

    private fun initView() {
        views.buttonTest.singleClick { startTest() }
        views.buttonContract.singleClick { Intent(this, ContractActivity::class.java).launch() }
    }

    private fun startTest() {
        CoroutinesHolder.main.launch {
            loading.show()
            runCatching {
                measureTimeMillis {
                    LogUtil.test.debug("start test")
                    test()
                }.let {
                    LogUtil.test.debug("used ${AppTime.getTimeString(it)}")
                }
            }.getOrElse {
                LogUtil.test.error("exception@MainActivity.test", it)
            }
            loading.dismiss()
        }
    }

    private suspend fun test() {
        //...
        delay(1000)
    }
}