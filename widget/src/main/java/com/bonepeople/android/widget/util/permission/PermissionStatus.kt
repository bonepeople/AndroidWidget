package com.bonepeople.android.widget.util.permission

/**
 * Current runtime permission status.
 */
enum class PermissionStatus {
    GRANTED,
    DENIED,
    PERMANENTLY_DENIED;

    /**
     * Returns whether this status is [GRANTED].
     */
    fun isGranted(): Boolean = this == GRANTED
}