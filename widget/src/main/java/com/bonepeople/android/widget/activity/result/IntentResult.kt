package com.bonepeople.android.widget.activity.result

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import kotlinx.coroutines.CompletableDeferred

/**
 * Intent Result
 * + Handles and processes data returned by an [Activity].
 *
 * [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/ActivityResult)
 */
class IntentResult {
    private var resultAction: ((ActivityResult) -> Unit)? = null
    private var successAction: ((Intent?) -> Unit)? = null
    private var failureAction: ((ActivityResult) -> Unit)? = null
    private var activityResult: ActivityResult? = null
    private val resultReady = CompletableDeferred<Unit>()

    internal fun saveResult(result: ActivityResult) {
        activityResult = result
        try {
            this.resultAction?.invoke(result)
            if (result.resultCode == Activity.RESULT_OK) {
                successAction?.invoke(result.data)
            } else {
                failureAction?.invoke(result)
            }
        } finally {
            resultReady.complete(Unit)
        }
    }

    /**
     * Suspends until the [Activity] returns a result, then returns this handler for chained result processing.
     *
     * Cancelling the waiting coroutine does not cancel the launched [Activity].
     */
    suspend fun await(): IntentResult {
        resultReady.await()
        return this
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