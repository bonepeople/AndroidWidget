package com.bonepeople.android.widget.util.permission

/**
 * Current runtime permission status.
 */
enum class PermissionStatus {
    /** The permission is currently granted. */
    GRANTED,

    /**
     * The permission has not been requested yet.
     *
     * Note: Detection of this status is not currently implemented.
     */
    NOT_REQUESTED,

    /** The permission is denied but can still be requested normally. */
    DENIED,

    /** The permission cannot be obtained through the regular request flow. */
    REQUEST_BLOCKED;

    /**
     * Returns whether this status is [GRANTED].
     */
    fun isGranted(): Boolean = this == GRANTED

    /**
     * Returns whether this status allows a regular permission request.
     */
    fun isRequestable(): Boolean = this == NOT_REQUESTED || this == DENIED
}