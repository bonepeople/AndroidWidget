Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppSystem

## Introduction

`AppSystem` provides access to system-level information and features on Android devices, including battery status, IP addresses, screen dimensions, and process info.

## Use Cases

- Diagnostics and device monitoring
- Network operations and UI layout adjustments

## Features

- Battery percentage and change listener
- Device ID and process name
- IPv4/IPv6 address and broadcast address retrieval
- Status bar, navigation bar, and screen dimensions
- Running service check

## Usage

Battery percentage (0–100):

```kotlin
val percent = AppSystem.batteryPercent
```

Listen for battery changes:

```kotlin
val receiver = AppSystem.registerBatteryChanged(context) { percent ->
    // Handle battery level update
}
// Remember to unregister the receiver when done
context.unregisterReceiver(receiver)
```

Device and process info:

```kotlin
val androidId = AppSystem.androidId
val processName = AppSystem.processName
```

IP addresses (requires `INTERNET` permission):

```kotlin
val ipv4List = AppSystem.getIpAddressV4()
val ipv6List = AppSystem.getIpAddressV6()
val broadcast = AppSystem.getBroadcastAddress()
```

Screen and system UI dimensions:

```kotlin
val statusBarHeight = AppSystem.getStatusBarHeight()
val navigationBarHeight = AppSystem.getNavigationBarHeight()
val screenWidth = AppSystem.getScreenWidth()
val screenHeight = AppSystem.getScreenHeight()
```

Check running services:

```kotlin
val isRunning = AppSystem.checkServiceRunning(MyService::class.java)
```

## Notes

- On Android 8.0+ (API 26+), service checking is restricted and may not be accurate.

## Source Code

[AppSystem.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSystem.kt)