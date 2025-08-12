多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppTime

## 简介

`AppTime` 用于格式化与转换时间戳和时长，支持自定义输出格式、时区和地区设置。

## 场景

- 在日志、界面和报表中展示可读时间字符串
- 计时器风格的时长展示

## 功能

- 时间戳格式化，可选毫秒
- 自定义 pattern、时区和 locale
- 时长格式化为计时器字符串（可选完整单位和毫秒控制）
- 兼容 API 26 前后版本

## 使用方式

格式化时间戳为可读日期时间：

```kotlin
val result = AppTime.getDateTimeString(System.currentTimeMillis())
// "2025/6/30 14:33:45"

val withMillis = AppTime.getDateTimeString(System.currentTimeMillis(), withMillis = true)
// "2025/6/30 14:33:45.123"
```

自定义 pattern 格式化：

```kotlin
val result = AppTime.formatTime(
    timestamp = System.currentTimeMillis(),
    pattern = "yyyy-MM-dd HH:mm:ss.SSS",
    timeZone = TimeZone.getTimeZone("GMT+8"),
    local = Locale.US
)
// "2025-06-30 14:33:45.123"
```

格式化时长（毫秒）为计时器字符串：

```kotlin
val timer = AppTime.getTimeString(3661999)
// "1:01:01.999"

val fullTimer = AppTime.getTimeString(5999, fullTimeString = true)
// "00:00:05.999"

val noMillis = AppTime.getTimeString(5999, withMillis = false)
// "5"
```

## 源码链接

[AppTime.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppTime.kt)