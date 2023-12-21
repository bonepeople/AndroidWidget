package com.bonepeople.android.widget.activity.result

import android.app.Activity
import android.content.Intent
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.ActivityHolder.getExtra

/**
 * 启动[Intent]并返回[IntentResult]，用于获取Activity的返回结果
 * + 内部使用了[Activity.startActivityForResult]方法，会导致标记为singleTop、singleTask、singleInstance的Activity创建新的实例，调用时请手动为intent添加flag
 */
fun Intent.launch(): IntentResult {
    val data = ActivityHolder.getTopActivity()?.getExtra("com.bonepeople.android.widget.activity.result.IntentLauncher")
    if (data is ArrayList<*>) {
        data.firstOrNull {
            it is IntentLauncher && it.ready()
        }?.let {
            return (it as IntentLauncher).launch(this)
        }
        val recordLog: String = IntentLauncher.stackTraceToString()
        throw IllegalStateException("同时使用的IntentLauncher数量超出上限，可以通过IntentLauncher.initialCapacity调整初始化的上限\n$recordLog")
    } else {
        throw IllegalStateException("${ActivityHolder.getTopActivity()?.javaClass}需要继承自ComponentActivity才能使用此方法")
    }
}