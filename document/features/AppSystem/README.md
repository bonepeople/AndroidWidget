Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppSystem Usage Guide

**Source Code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSystem.kt  
**This document was created with the assistance of ChatGPT.**

## Overview

`AppSystem` is a utility object that provides access to various system-level information and features on Android devices, including battery status, IP addresses, screen dimensions, and more.

> Useful for diagnostics, network operations, UI layout adjustments, and device monitoring.

## How to Use

### 1. Get Battery Information

#### Battery percentage (0–100):

```kotlin
val percent = AppSystem.batteryPercent
```

#### Listen for battery changes:

```kotlin
val receiver = AppSystem.registerBatteryChanged(context) { percent ->
    // Handle battery level update
}
// Remember to unregister the receiver when done
context.unregisterReceiver(receiver)
```

### 2. Get Device and Process Info

```kotlin
val androidId = AppSystem.androidId
val processName = AppSystem.processName
```

### 3. Retrieve IP Addresses (Requires INTERNET permission)

#### IPv4 addresses:

```kotlin
val ipv4List = AppSystem.getIpAddressV4()
```

#### IPv6 addresses:

```kotlin
val ipv6List = AppSystem.getIpAddressV6()
```

#### Broadcast address:

```kotlin
val broadcast = AppSystem.getBroadcastAddress()
```

### 4. Screen and System UI Dimensions

```kotlin
val statusBarHeight = AppSystem.getStatusBarHeight()
val navigationBarHeight = AppSystem.getNavigationBarHeight()
val screenWidth = AppSystem.getScreenWidth()
val screenHeight = AppSystem.getScreenHeight()
```

### 5. Check Running Services

```kotlin
val isRunning = AppSystem.checkServiceRunning(MyService::class.java)
```

> ⚠ On Android 8.0+ (API 26+), service checking is restricted and may not be accurate.