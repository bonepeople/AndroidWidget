package com.bonepeople.android.widget.activity.result

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult

/**
 * Intent Result
 * + Handles and processes data returned by an [Activity].
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
     * Sets the action to be executed when the result is received from the [Activity].
     */
    fun onResult(action: (ActivityResult) -> Unit) = apply {
        resultAction = action
        activityResult?.let(action)
    }

    /**
     * Sets the action to be executed when the [Activity] returns a result with [Activity.RESULT_OK].
     *
     * The parameter provides the returned [Intent].
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
     * Sets the action to be executed when the [Activity] returns a result other than [Activity.RESULT_OK].
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