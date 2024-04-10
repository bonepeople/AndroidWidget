package com.bonepeople.android.widget.activity.result

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult

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

    fun onResult(action: (ActivityResult) -> Unit) = apply {
        resultAction = action
        activityResult?.let(action)
    }

    fun onSuccess(action: (Intent?) -> Unit) = apply {
        successAction = action
        activityResult?.let {
            if (it.resultCode == Activity.RESULT_OK) {
                action.invoke(it.data)
            }
        }
    }

    fun onFailure(action: (ActivityResult) -> Unit) = apply {
        failureAction = action
        activityResult?.let {
            if (it.resultCode != Activity.RESULT_OK) {
                action.invoke(it)
            }
        }
    }
}