package com.bonepeople.android.widget.activity.result

import android.content.Intent
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.ActivityHolder.getExtra

/**
 * 启动[Intent]并返回[IntentCallback]，用于获取Activity的返回结果
 */
fun Intent.launch(): IntentCallback {
    ActivityHolder.getTopActivity()?.getExtra("com.bonepeople.android.widget.activity.result.IntentCallback")?.let {
        val intentCallback = it as IntentCallback
        intentCallback.launch(this)
        return intentCallback
    }
    throw IllegalStateException("${ActivityHolder.getTopActivity()?.javaClass}需要继承自ComponentActivity")
}