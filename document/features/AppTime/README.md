Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppTime

## Introduction

`AppTime` formats and converts timestamps and durations. It supports custom output patterns, time zones, and locale-specific formatting.

## Use Cases

- Readable time strings in logs, UIs, and reports
- Timer-style duration display

## Features

- Timestamp formatting with optional milliseconds
- Custom pattern, time zone, and locale support
- Duration formatting as timer strings (with optional full units and millisecond control)
- Compatible with both pre-API 26 and modern Android versions

## Usage

Format timestamp as a readable date-time:

```kotlin
val result = AppTime.getDateTimeString(System.currentTimeMillis())
// "2025/6/30 14:33:45"

val withMillis = AppTime.getDateTimeString(System.currentTimeMillis(), withMillis = true)
// "2025/6/30 14:33:45.123"
```

Format timestamp with a custom pattern:

```kotlin
val result = AppTime.formatTime(
    timestamp = System.currentTimeMillis(),
    pattern = "yyyy-MM-dd HH:mm:ss.SSS",
    timeZone = TimeZone.getTimeZone("GMT+8"),
    local = Locale.US
)
// "2025-06-30 14:33:45.123"
```

Format duration (milliseconds) as a timer string:

```kotlin
val timer = AppTime.getTimeString(3661999)
// "1:01:01.999"

val fullTimer = AppTime.getTimeString(5999, fullTimeString = true)
// "00:00:05.999"

val noMillis = AppTime.getTimeString(5999, withMillis = false)
// "5"
```

## Source Code

[AppTime.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppTime.kt)