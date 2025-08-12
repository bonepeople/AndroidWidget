Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppPermission

## Introduction

`AppPermission` simplifies runtime permission checks and requests in Android applications. It uses `ActivityResultContract` internally and triggers the request flow immediately when `request()` is called.

## Features

- Check or request multiple permissions in one call
- Only requests permissions that are not yet granted
- `onGranted` callback when all requested permissions are granted
- `onResult` callback with a detailed permission state map

Unlike some other frameworks, calling `AppPermission.request()` immediately triggers the request process. You are not forced to set callbacks or invoke a separate "apply" operation.

## Usage

Check permissions:

```kotlin
AppPermission.check(android.Manifest.permission.CAMERA)
    .onGranted {
        // All requested permissions are granted
    }
    .onResult { allGranted, resultMap ->
        // Handle the result map if needed
    }
```

Request permissions:

```kotlin
AppPermission.request(
    android.Manifest.permission.CAMERA,
    android.Manifest.permission.READ_EXTERNAL_STORAGE
).onGranted {
    // All permissions granted
}.onResult { allGranted, resultMap ->
    if (!allGranted) {
        // Handle denied permissions
    }
}
```

## Notes

- `onGranted` is only triggered when **all** requested permissions are granted. Use `onResult` when you need to handle denial cases.

## Source Code

[AppPermission.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppPermission.kt)