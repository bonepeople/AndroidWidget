package com.bonepeople.android.widget.activity.result

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult

/**
 * Intent结果
 * + 接收并处理[Activity]返回的数据
 */
class IntentResult {
    private var resultAction: ((ActivityResult) -> Unit)? = null
    private var successAction: ((Intent?) -> Unit)? = null
    private var failureAction: ((ActivityResult) -> Unit)? = null
    private var activityResult: ActivityResult? = null

    internal fun saveResult(result: ActivityResult) {
        activityResult = result
        this.resultAction?.invoke(result)
        if (result.resultCode == Activity.RESULT_OK) {
            successAction?.invoke(result.data)
        } else {
            failureAction?.invoke(result)
        }
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
}