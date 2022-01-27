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

class IntentLauncher : ActivityResultCallback<ActivityResult> {
    private var launcher: ActivityResultLauncher<Intent>? = null
    private var intent: Intent? = null
    private var result: ((ActivityResult) -> Unit)? = null
    private var success: ((Intent?) -> Unit)? = null
    private var failure: ((ActivityResult) -> Unit)? = null

    internal fun prepare(intent: Intent) {
        this.intent = intent
        result = null
        success = null
        failure = null
    }

    fun onResult(method: (ActivityResult) -> Unit) = apply {
        result = method
    }

    fun onSuccess(method: (Intent?) -> Unit) = apply {
        success = method
    }

    fun onFailure(method: (ActivityResult) -> Unit) = apply {
        failure = method
    }

    fun launch() {
        intent?.let {
            launcher?.launch(it)
        }
    }

    override fun onActivityResult(result: ActivityResult) {
        this.result?.invoke(result)
        if (result.resultCode == Activity.RESULT_OK) {
            success?.invoke(result.data)
        } else {
            failure?.invoke(result)
        }
    }

    internal object Register : DefaultActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            if (activity is ComponentActivity) {
                val intentLauncher = IntentLauncher()
                intentLauncher.launcher = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult(), intentLauncher)
                activity.putExtra("com.bonepeople.android.widget.activity.result.IntentLauncher", intentLauncher)
            }
        }
    }
}