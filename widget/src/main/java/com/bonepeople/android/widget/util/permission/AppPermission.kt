package com.bonepeople.android.widget.util.permission

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.core.content.ContextCompat
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
     * Returns whether all permissions are currently granted.
     */
    fun checkGranted(vararg permissions: String): Boolean {
        return permissions.all { checkStatus(it) == PermissionStatus.GRANTED }
    }

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
            val deniedPermissions = permissionStatuses
                .filterValues { it == PermissionStatus.DENIED }
                .keys
                .toTypedArray()

            if (deniedPermissions.isEmpty()) {
                return@withContext PermissionResult(permissionStatuses)
            }

            suspendCancellableCoroutine { continuation ->
                val contract = RequestMultiplePermissions()
                contract.createIntent(ApplicationHolder.app, deniedPermissions).launch()
                    .onResult { result ->
                        contract.parseResult(result.resultCode, result.data).forEach { (permission, granted) ->
                            permissionStatuses[permission] = if (granted) PermissionStatus.GRANTED else PermissionStatus.DENIED
                        }
                        continuation.resume(PermissionResult(permissionStatuses))
                    }
            }
        }
    }
}