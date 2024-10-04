多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppSystem 使用指南

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSystem.kt  
**本文档由 ChatGPT 协助完成。**

## 简介

`AppSystem` 是一个系统工具类，可用于获取设备的系统信息，如电量、IP 地址、屏幕尺寸、状态栏高度、服务运行状态等。

> 适用于网络诊断、界面自适应、系统监控等场景。

## 使用方法

### 1. 获取电池信息

#### 当前电量百分比（0–100）：

```kotlin
val percent = AppSystem.batteryPercent
```

#### 监听电量变化：

```kotlin
val receiver = AppSystem.registerBatteryChanged(context) { percent ->
    // 处理电量变化
}
// 记得在不使用时注销广播
context.unregisterReceiver(receiver)
```

### 2. 获取设备 ID 和进程名称

```kotlin
val androidId = AppSystem.androidId
val processName = AppSystem.processName
```

### 3. 获取设备 IP 地址（需 INTERNET 权限）

#### IPv4 地址：

```kotlin
val ipv4List = AppSystem.getIpAddressV4()
```

#### IPv6 地址：

```kotlin
val ipv6List = AppSystem.getIpAddressV6()
```

#### 网络广播地址：

```kotlin
val broadcast = AppSystem.getBroadcastAddress()
```

### 4. 屏幕和系统界面尺寸

```kotlin
val statusBarHeight = AppSystem.getStatusBarHeight()
val navigationBarHeight = AppSystem.getNavigationBarHeight()
val screenWidth = AppSystem.getScreenWidth()
val screenHeight = AppSystem.getScreenHeight()
```

### 5. 检查服务是否运行中

```kotlin
val running = AppSystem.checkServiceRunning(MyService::class.java)
```

> ⚠ 注意：在 Android 8.0+（API 26 以上）系统中，服务检测功能受限，可能无法准确获取运行状态。