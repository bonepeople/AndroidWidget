Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppLog

## Introduction

`AppLog` is a lightweight logging utility for Android. It wraps the native `Log` class and adds thread info, stack trace info, and reusable instances per tag.

## Use Cases

- Logging per module or feature using custom tags
- Including thread or method location for debugging
- Centralized and controllable logging setup

## Features

- Five log levels: `verbose`, `debug`, `info`, `warn`, `error`
- Global and per-instance log enabling
- Optional thread name and caller location display
- Reusable instances per tag

## Usage

Get the default logger:

```kotlin
AppLog.defaultLog.debug("App started.")
```

Create a logger with a specific tag:

```kotlin
val log = AppLog.tag("MainActivity")
log.info("View created")
```

Use different log levels:

```kotlin
log.verbose("This is a verbose message")
log.debug("This is a debug message")
log.info("This is an info message")
log.warn("This is a warning")
log.error("This is an error")
```

Enable thread and stack trace info:

```kotlin
log.showThreadInfo = true
log.showStackInfo = true
log.stackOffset = 6 // Adjust based on stack level
log.debug("With stack and thread info") // [main] With stack and thread info @ MainActivity$startTest$1.invokeSuspend:38
```

Control global logging:

```kotlin
AppLog.enable = false // Disable all logs globally
```

## Notes

- Adjust `stackOffset` to match your method call depth.
- `AppLog.enable` is a global switch; `AppLog.tag(...).enable` is instance-level.
- Avoid excessive debug logs in production builds for performance reasons.

## Source Code

[AppLog.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppLog.kt)