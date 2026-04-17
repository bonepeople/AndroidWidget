Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppPermission

## Introduction

`AppPermission` provides synchronous runtime permission checks and coroutine-based permission requests. Permission requests use AndroidX `ActivityResultContract` internally, start as soon as `request()` is executed, and return their result directly without callback chains or temporary variables.

## Features

- Query the current status of an individual permission synchronously
- Request multiple permissions from a coroutine
- Distinguish between a regular denial and a blocked request after a request
- Uses AndroidX `ActivityResultContracts.RequestMultiplePermissions` for the system permission flow
- Starts the permission flow when `request()` is called, with no separate callback registration or launch step
- Only requests permissions that are not yet granted
- Returns whether all permissions were granted and the status of every permission

## Usage

Import the new permission utility:

```kotlin
import androidx.lifecycle.lifecycleScope
import com.bonepeople.android.widget.util.permission.AppPermission
import com.bonepeople.android.widget.util.permission.PermissionStatus
import kotlinx.coroutines.launch
```

Check whether all permissions in a collection are granted:

```kotlin
val permissions = arrayOf(
    android.Manifest.permission.CAMERA,
    android.Manifest.permission.ACCESS_COARSE_LOCATION
)
if (permissions.all { AppPermission.checkStatus(it).isGranted() }) {
    // All permissions are granted
}
```

Query an individual permission:

```kotlin
when (AppPermission.checkStatus(android.Manifest.permission.CAMERA)) {
    PermissionStatus.GRANTED -> openCamera()
    PermissionStatus.NOT_REQUESTED -> showFirstRequestHint()
    PermissionStatus.DENIED -> requestPermission()
    PermissionStatus.REQUEST_BLOCKED -> openAppSettings()
}
```

If you only need to check whether an individual status is granted, use `PermissionStatus.isGranted()`:

```kotlin
if (AppPermission.checkStatus(android.Manifest.permission.CAMERA).isGranted()) {
    openCamera()
}
```

Request permissions from a lifecycle-aware coroutine:

```kotlin
lifecycleScope.launch {
    val result = AppPermission.request(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    if (result.allGranted()) {
        startFeature()
    }

    val cameraStatus = result.permissionStatuses[android.Manifest.permission.CAMERA]
    if (cameraStatus == PermissionStatus.REQUEST_BLOCKED) {
        openAppSettings()
    }
}
```

## Migrating from the Callback API

Replace callback chains:

```kotlin
com.bonepeople.android.widget.util.AppPermission.request(permission)
    .onResult { allGranted, permissionResult ->
        if (allGranted) startFeature()
        val granted = permissionResult[permission] == true
    }
```

with the coroutine API:

```kotlin
lifecycleScope.launch {
    val result = AppPermission.request(permission)
    if (result.allGranted()) startFeature()
    val status = result.permissionStatuses[permission]
}
```

## Notes

- Use `permissions.all { AppPermission.checkStatus(it).isGranted() }` to check multiple permissions. `all` returns `true` for an empty collection.
- `request()` is a suspending function and must be called from a coroutine. A lifecycle-aware scope such as `lifecycleScope` is recommended.
- `request()` skips permissions that are already granted. Its result still contains every supplied permission in the original order.
- After a permission is denied, `request()` uses `ActivityCompat.shouldShowRequestPermissionRationale()` to determine whether the regular request flow is blocked and returns `REQUEST_BLOCKED` when it is.
- `REQUEST_BLOCKED` means the regular permission request flow is currently blocked. This commonly occurs after the user chooses "Don't ask again" or denies the permission repeatedly; the status does not identify the specific cause.
- The legacy callback API remains available at `com.bonepeople.android.widget.util.AppPermission`, but new code should use the API documented here.
- Detection of `NOT_REQUESTED` is not currently implemented, so an ungranted permission temporarily returns `DENIED`.

## Source Code

- [AppPermission.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/permission/AppPermission.kt)
- [PermissionResult.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/permission/PermissionResult.kt)
- [PermissionStatus.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/permission/PermissionStatus.kt)