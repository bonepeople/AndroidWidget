Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppPermission Usage Guide

**Source Code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppPermission.kt  
**This document was created with the assistance of ChatGPT.**

## Overview

`AppPermission` is a utility class for handling runtime permission checks and requests in Android applications. It leverages `ActivityResultContract` to simplify the process and streamline permission flow management.

> This utility is designed to be simple and flexible.
> - You can apply for multiple permissions at once.
> - Only permissions that are not yet granted will be requested.
> - Unlike other frameworks, calling `AppPermission.request()` immediately triggers the request process. You are not forced to set callbacks or invoke a separate "apply" operation.
> - The `onGranted` callback is only triggered when **all** requested permissions are granted. It is ideal for logic that doesn't require handling denial cases.

## How to Use

### 1. Checking Permissions

You can check if permissions are granted using the `check()` method:

```kotlin
val permission = AppPermission.check(android.Manifest.permission.CAMERA)
    .onGranted {
        // All requested permissions are granted
    }
    .onResult { allGranted, resultMap ->
        // Handle the result map if needed
    }
```

### 2. Requesting Permissions

To request permissions from the user:

```kotlin
val permission = AppPermission.request(
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