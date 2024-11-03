package com.bonepeople.android.widget.activity.result

import android.app.Activity
import android.content.Intent
import com.bonepeople.android.widget.ActivityHolder

/**
 * Launches the [Intent] and returns an [IntentResult], used to obtain the result returned by an Activity.
 * + Internally uses the [Activity.startActivityForResult] method, which may cause Activities with
 *   singleTop, singleTask, or singleInstance launch modes to create new instances.
 *   Ensure to manually add the appropriate flags to the Intent when calling this method.
 *
 *   [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ActivityResult)
 */
fun Intent.launch(): IntentResult {
    val data = ActivityHolder.getTopActivity()?.let { ActivityHolder.getData(it, "com.bonepeople.android.widget.activity.result.IntentLauncher") }
    if (data is ArrayList<*>) {
        data.firstOrNull {
            it is IntentLauncher && it.ready()
        }?.let {
            return (it as IntentLauncher).launch(this)
        }
        val recordLog: String = IntentLauncher.stackTraceToString()
        throw IllegalStateException("The number of simultaneously used IntentLaunchers exceeds the limit. You can adjust the limit by modifying IntentLauncher.initialCapacity.\n$recordLog")
    } else {
        throw IllegalStateException("${ActivityHolder.getTopActivity()?.javaClass} must inherit from ComponentActivity to use this method.")
    }
}