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
 * ActivityResultLauncher返回结果的处理器
 */
class IntentCallback private constructor() : ActivityResultCallback<ActivityResult> {
    private var launcher: ActivityResultLauncher<Intent>? = null
    private var resultAction: ((ActivityResult) -> Unit)? = null
    private var successAction: ((Intent?) -> Unit)? = null
    private var failureAction: ((ActivityResult) -> Unit)? = null
    private var activityResult: ActivityResult? = null

    internal fun launch(intent: Intent) {
        resultAction = null
        successAction = null
        failureAction = null
        activityResult = null
        launcher?.launch(intent)
    }

    /**
     * 设置收到Activity的返回结果时执行的动作
     */
    fun onResult(action: (ActivityResult) -> Unit) = apply {
        resultAction = action
        activityResult?.let(action)
    }

    /**
     * 设置Activity的返回结果为[Activity.RESULT_OK]时的动作
     *
     * 附带参数为返回的[Intent]
     */
    fun onSuccess(action: (Intent?) -> Unit) = apply {
        successAction = action
        activityResult?.let {
            if (it.resultCode == Activity.RESULT_OK) {
                action.invoke(it.data)
            }
        }
    }

    /**
     * 设置Activity的返回结果不为[Activity.RESULT_OK]时的动作
     */
    fun onFailure(action: (ActivityResult) -> Unit) = apply {
        failureAction = action
        activityResult?.let {
            if (it.resultCode != Activity.RESULT_OK) {
                action.invoke(it)
            }
        }
    }

    override fun onActivityResult(result: ActivityResult) {
        this.resultAction?.invoke(result)
        if (result.resultCode == Activity.RESULT_OK) {
            successAction?.invoke(result.data)
        } else {
            failureAction?.invoke(result)
        }
        activityResult = result
    }

    internal object Register : DefaultActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (activity is ComponentActivity) {
                val intentCallback = IntentCallback()
                intentCallback.launcher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult(), intentCallback)
                activity.putExtra("com.bonepeople.android.widget.activity.result.IntentCallback", intentCallback)
            }
        }
    }
}