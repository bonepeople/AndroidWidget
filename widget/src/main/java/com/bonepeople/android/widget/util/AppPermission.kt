package com.bonepeople.android.widget.util

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.content.ContextCompat
import com.bonepeople.android.widget.ApplicationHolder
import com.bonepeople.android.widget.activity.result.launch
import java.util.*
import kotlin.collections.LinkedHashMap

/**
 * Permission request utility class
 *
 * Utilizes ActivityResultContract to request permissions
 */
@Suppress("UNUSED")
class AppPermission private constructor() {
    private val permissionResult = LinkedHashMap<String, Boolean>()
    private var resultAction: (Boolean, LinkedHashMap<String, Boolean>) -> Unit = { _, _ -> }
    private var grantedAction: () -> Unit = {}
    private var finished = false

    /**
     * Sets the action to perform when all permissions are granted
     */
    fun onGranted(action: () -> Unit) = apply {
        if (finished) {
            if (permissionResult.values.all { it }) {
                action.invoke()
            }
        } else {
            grantedAction = action
        }
    }

    /**
     * Sets the action to perform when permission request results are obtained
     *
     * @param action a lambda with:
     * - allGranted: whether all permissions are granted
     * - permissionResult: a map of each permission and its corresponding result
     */
    fun onResult(action: (allGranted: Boolean, permissionResult: LinkedHashMap<String, Boolean>) -> Unit) = apply {
        if (finished) {
            val granted = permissionResult.values.all { it }
            action.invoke(granted, permissionResult)
        } else {
            resultAction = action
        }
    }

    companion object {
        /**
         * Checks the status of specified permissions
         */
        fun check(vararg permissions: String): AppPermission {
            return AppPermission().apply {
                permissions.forEach {
                    permissionResult[it] = ContextCompat.checkSelfPermission(ApplicationHolder.app, it) == PackageManager.PERMISSION_GRANTED
                }
                finished = true
            }
        }

        /**
         * Initiates a permission request
         */
        fun request(vararg permissions: String): AppPermission {
            return AppPermission().apply {
                val requestList = LinkedList<String>()
                permissions.forEach {
                    if (ContextCompat.checkSelfPermission(ApplicationHolder.app, it) == PackageManager.PERMISSION_GRANTED) {
                        permissionResult[it] = true
                    } else {
                        permissionResult[it] = false
                        requestList.add(it)
                    }
                }
                if (requestList.isEmpty()) { // All permissions already granted
                    finished = true
                } else { // Request permissions not yet granted
                    RequestMultiplePermissions().createIntent(ApplicationHolder.app, requestList.toTypedArray()).launch()
                        .onResult { result ->
                            permissionResult.putAll(RequestMultiplePermissions().parseResult(result.resultCode, result.data))
                            val granted = permissionResult.values.all { it }
                            resultAction.invoke(granted, permissionResult)
                            if (granted) grantedAction.invoke()
                            finished = true
                        }
                }
            }
        }
    }
}