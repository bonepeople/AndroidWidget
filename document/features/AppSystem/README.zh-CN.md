多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppSystem

## 简介

`AppSystem` 提供 Android 设备系统级信息与功能的访问，包括电池状态、IP 地址、屏幕尺寸和进程信息等。

## 场景

- 诊断与设备监控
- 网络操作与 UI 布局调整

## 功能

- 电池电量及变化监听
- 设备 ID 与进程名
- IPv4/IPv6 地址及广播地址获取
- 状态栏、导航栏和屏幕尺寸
- 运行中服务检测

## 使用方式

电池电量（0–100）：

```kotlin
val percent = AppSystem.batteryPercent
```

监听电池变化：

```kotlin
val receiver = AppSystem.registerBatteryChanged(context) { percent ->
    // 处理电量更新
}
// 使用完毕后记得注销
context.unregisterReceiver(receiver)
```

设备与进程信息：

```kotlin
val androidId = AppSystem.androidId
val processName = AppSystem.processName
```

IP 地址（需要 `INTERNET` 权限）：

```kotlin
val ipv4List = AppSystem.getIpAddressV4()
val ipv6List = AppSystem.getIpAddressV6()
val broadcast = AppSystem.getBroadcastAddress()
```

屏幕与系统 UI 尺寸：

```kotlin
val statusBarHeight = AppSystem.getStatusBarHeight()
val navigationBarHeight = AppSystem.getNavigationBarHeight()
val screenWidth = AppSystem.getScreenWidth()
val screenHeight = AppSystem.getScreenHeight()
```

检测运行中的服务：

```kotlin
val isRunning = AppSystem.checkServiceRunning(MyService::class.java)
```

## 注意事项

- Android 8.0+（API 26+）对服务检测有限制，结果可能不准确。

## 源码链接

[AppSystem.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSystem.kt)