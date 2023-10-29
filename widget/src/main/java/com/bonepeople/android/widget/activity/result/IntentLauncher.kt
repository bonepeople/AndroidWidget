package com.bonepeople.android.widget.activity.result

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.bonepeople.android.widget.ActivityHolder.putExtra
import com.bonepeople.android.widget.DefaultActivityLifecycleCallbacks

/**
 * Intent启动器
 * + 在每个[ComponentActivity]创建的时候都会配套创建[IntentLauncher]，用于启动用户指定的[Intent]
 */
class IntentLauncher private constructor() : ActivityResultCallback<ActivityResult> {
    private var launcher: ActivityResultLauncher<Intent>? = null
    private var intentResult: IntentResult? = null

    internal fun launch(intent: Intent): IntentResult {
        val tempResult = IntentResult()
        intentResult = tempResult
        launcher?.launch(intent)
        return tempResult
    }

    internal fun ready() = intentResult == null

    override fun onActivityResult(result: ActivityResult) {
        val tempResult = intentResult
        intentResult = null
        tempResult?.saveResult(result)
    }

    companion object {
        /**
         * IntentLauncher的初始化数量
         * + 该变量用于控制在每个Activity中初始化IntentLauncher的数量
         * + 过多的初始化IntentLauncher会造成资源的浪费，请根据需求进行调整
         */
        var initialCapacity = 1
    }

    internal object Registry : DefaultActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (activity is ComponentActivity) {
                val launcherList = ArrayList<IntentLauncher>()
                for (i in 1..initialCapacity) {
                    val intentLauncher = IntentLauncher()
                    intentLauncher.launcher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult(), intentLauncher)
                    launcherList.add(intentLauncher)
                }
                activity.putExtra("com.bonepeople.android.widget.activity.result.IntentLauncher", launcherList)
            }
        }
    }
}