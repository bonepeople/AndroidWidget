多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppTime 使用指南

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppTime.kt  
**本文档由 ChatGPT 协助完成。**

## 简介

`AppTime` 是一个时间处理工具类，支持时间戳格式化、时区转换和持续时间（毫秒）转换为可读字符串。

> 常用于时间展示、日志格式化、倒计时显示等场景。

## 使用方法

### 1. 将时间戳转换为标准时间格式

```kotlin
val result = AppTime.getDateTimeString(System.currentTimeMillis())
// 结果："2025/6/30 14:33:45"
```

#### 包含毫秒：

```kotlin
val result = AppTime.getDateTimeString(System.currentTimeMillis(), withMillis = true)
// 结果："2025/6/30 14:33:45.123"
```

### 2. 使用自定义格式化模板

```kotlin
val result = AppTime.formatTime(
    timestamp = System.currentTimeMillis(),
    pattern = "yyyy-MM-dd HH:mm:ss.SSS",
    timeZone = TimeZone.getTimeZone("GMT+8"),
    local = Locale.SIMPLIFIED_CHINESE
)
// 结果："2025-06-30 14:33:45.123"
```

> 支持 Android 旧版本和 8.0+ 系统。

### 3. 将毫秒转换为定时器样式

```kotlin
val timer = AppTime.getTimeString(3661999)
// 结果："1:01:01.999"
```

#### 强制显示小时与分钟：

```kotlin
val timer = AppTime.getTimeString(5999, fullTimeString = true)
// 结果："00:00:05.999"
```

#### 不显示毫秒：

```kotlin
val timer = AppTime.getTimeString(5999, withMillis = false)
// 结果："5"
```