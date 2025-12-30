package com.bonepeople.android.widget.util.permission

/**
 * Result of a permission request, including the status of every requested permission.
 *
 * @property permissionStatuses permission statuses in the original permission order
 */
data class PermissionResult(
    val permissionStatuses: Map<String, PermissionStatus>
) {
    /**
     * Returns whether all permissions are granted.
     */
    fun allGranted(): Boolean {
        return permissionStatuses.values.all { it == PermissionStatus.GRANTED }
    }
}