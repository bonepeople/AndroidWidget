Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppTime Usage Guide

**Source Code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppTime.kt  
**This document was created with the assistance of ChatGPT.**

## Overview

`AppTime` is a utility class for formatting and converting timestamps and durations. It supports various output formats, time zones, and locale-specific formatting.

> Helpful for presenting readable time strings in logs, UIs, and reports.

## How to Use

### 1. Format Timestamp as a Readable Date-Time

```kotlin
val result = AppTime.getDateTimeString(System.currentTimeMillis())
// result: "2025/6/30 14:33:45"
```

#### Include milliseconds:

```kotlin
val result = AppTime.getDateTimeString(System.currentTimeMillis(), withMillis = true)
// result: "2025/6/30 14:33:45.123"
```

### 2. Format Timestamp with Custom Pattern

```kotlin
val result = AppTime.formatTime(
    timestamp = System.currentTimeMillis(),
    pattern = "yyyy-MM-dd HH:mm:ss.SSS",
    timeZone = TimeZone.getTimeZone("GMT+8"),
    local = Locale.CHINA
)
// result: "2025-06-30 14:33:45.123"
```

> Supports both pre-API 26 and modern Android versions.

### 3. Format Duration (Milliseconds) as Timer String

```kotlin
val timer = AppTime.getTimeString(3661999)
// result: "1:01:01.999"
```

#### Always show full time units (hours, minutes):

```kotlin
val timer = AppTime.getTimeString(5999, fullTimeString = true)
// result: "00:00:05.999"
```

#### Omit milliseconds:

```kotlin
val timer = AppTime.getTimeString(5999, withMillis = false)
// result: "5"
```