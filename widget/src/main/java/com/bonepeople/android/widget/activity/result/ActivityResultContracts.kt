package com.bonepeople.android.widget.activity.result

import android.content.Intent
import com.bonepeople.android.widget.ActivityHolder

fun Intent.launch(): IntentResult {
    val data = ActivityHolder.getTopActivity()?.let { ActivityHolder.getData(it, "com.bonepeople.android.widget.activity.result.IntentLauncher") }
    if (data is ArrayList<*>) {
        data.firstOrNull {
            it is IntentLauncher && it.ready()
        }?.let {
            return (it as IntentLauncher).launch(this)
        }
        val recordLog: String = IntentLauncher.stackTraceToString()
        throw IllegalStateException(recordLog)
    } else {
        throw IllegalStateException("${ActivityHolder.getTopActivity()?.javaClass}")
    }
}