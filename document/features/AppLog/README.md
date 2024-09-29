Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppLog Usage Guide

> This document was generated with the help of ChatGPT  
> Source code link: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppLog.kt

## Introduction

`AppLog` is a lightweight and flexible logging utility for Android. It wraps the native `Log` class and adds useful features such as thread info, stack trace info, and multiple reusable instances by tag.

## Features

- Five log levels: `verbose`, `debug`, `info`, `warn`, `error`;
- Global and per-instance log enabling;
- Optional display of thread name;
- Optional display of class name and line number;
- Reusable instances per tag.

## How to Use

### 1. Get the default logger

```kotlin
AppLog.defaultLog.debug("App started.")
```

### 2. Create a logger with a specific tag

```kotlin
val log = AppLog.tag("MainActivity")
log.info("View created")
```

### 3. Use different log levels

```kotlin
log.verbose("This is a verbose message")
log.debug("This is a debug message")
log.info("This is an info message")
log.warn("This is a warning")
log.error("This is an error")
```

### 4. Enable thread and stack trace info

```kotlin
log.showThreadInfo = true
log.showStackInfo = true
log.stackOffset = 6 // Adjust based on stack level
log.debug("With stack and thread info") // [main] With stack and thread info @ MainActivity$startTest$1.invokeSuspend:38
```

### 5. Control global logging

```kotlin
AppLog.enable = false // Disable all logs globally
```

## Recommended Use Cases

- Logging per module or feature using custom tags;
- Including thread or method location for debugging;
- Centralized and controllable logging setup.

## Notes

- Adjust `stackOffset` to match your method call depth;
- `AppLog.enable` is a global switch; `AppLog.tag(...).enable` is instance-level;
- Avoid excessive debug logs in production builds for performance reasons.