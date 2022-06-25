package com.bonepeople.android.widget.activity.result

import android.content.Intent
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.ActivityHolder.getExtra

/**
 * 启动[Intent]并返回[IntentResult]，用于获取Activity的返回结果
 */
fun Intent.launch(): IntentResult {
    val data = ActivityHolder.getTopActivity()?.getExtra("com.bonepeople.android.widget.activity.result.IntentLauncher")
    if (data is ArrayList<*>) {
        data.firstOrNull {
            it is IntentLauncher && it.ready()
        }?.let {
            return (it as IntentLauncher).launch(this)
        }
        throw IllegalStateException("同时使用的IntentLauncher数量超出上限，可以通过IntentLauncher.initialCapacity调整初始化的上限")
    } else {
        throw IllegalStateException("${ActivityHolder.getTopActivity()?.javaClass}需要继承自ComponentActivity才能使用此方法")
    }
}