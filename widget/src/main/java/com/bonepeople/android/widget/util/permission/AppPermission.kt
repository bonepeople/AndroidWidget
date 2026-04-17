package com.bonepeople.android.widget.util.permission

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.ApplicationHolder
import com.bonepeople.android.widget.activity.result.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlin.coroutines.resume

/**
 * Utility for checking and requesting runtime permissions.
 *
 * Permission checks return synchronously. Permission requests suspend until the user completes the
 * system permission flow and use [RequestMultiplePermissions] internally.
 *
 * [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppPermission)
 */
@Suppress("Unused")
object AppPermission {
    /**
     * Returns the current status of a permission.
     */
    fun checkStatus(permission: String): PermissionStatus {
        return if (ContextCompat.checkSelfPermission(ApplicationHolder.app, permission) == PackageManager.PERMISSION_GRANTED) {
            PermissionStatus.GRANTED
        } else {
            PermissionStatus.DENIED
        }
    }

    /**
     * Requests permissions and suspends until the request is complete.
     */
    suspend fun request(vararg permissions: String): PermissionResult {
        return withContext(Dispatchers.Main.immediate) {
            val permissionStatuses = LinkedHashMap<String, PermissionStatus>().apply {
                permissions.forEach { permission ->
                    this[permission] = checkStatus(permission)
                }
            }
            val requestablePermissions = permissionStatuses
                .filterValues { it.isRequestable() }
                .keys
                .toTypedArray()

            if (requestablePermissions.isEmpty()) {
                return@withContext PermissionResult(permissionStatuses)
            }

            suspendCancellableCoroutine { continuation ->
                val activity = ActivityHolder.getTopActivity() ?: throw IllegalStateException("No active Activity is available to request permissions.")
                val contract = RequestMultiplePermissions()
                contract.createIntent(ApplicationHolder.app, requestablePermissions).launch()
                    .onResult { result ->
                        contract.parseResult(result.resultCode, result.data).forEach { (permission, granted) ->
                            permissionStatuses[permission] = when {
                                granted -> PermissionStatus.GRANTED
                                ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> PermissionStatus.DENIED
                                else -> PermissionStatus.REQUEST_BLOCKED
                            }
                        }
                        continuation.resume(PermissionResult(permissionStatuses))
                    }
            }
        }
    }
}