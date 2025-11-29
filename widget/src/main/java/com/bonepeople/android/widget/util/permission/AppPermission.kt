package com.bonepeople.android.widget.util.permission

import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.bonepeople.android.widget.ApplicationHolder

/**
 * Utility for handling app permissions.
 */
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
}