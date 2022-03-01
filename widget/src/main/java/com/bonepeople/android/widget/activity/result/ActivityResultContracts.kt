package com.bonepeople.android.widget.activity.result

import android.content.Intent
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.ActivityHolder.getExtra

/**
 * 为[Intent]创建一个[IntentLauncher]，用于获取Activity的返回结果
 */
fun Intent.createLauncher(): IntentLauncher {
    ActivityHolder.getTopActivity()?.getExtra("com.bonepeople.android.widget.activity.result.IntentLauncher")?.let {
        val intentLauncher = it as IntentLauncher
        intentLauncher.prepare(this)
        return intentLauncher
    }
    throw IllegalStateException("缺少可用的AppCompatActivity")
}