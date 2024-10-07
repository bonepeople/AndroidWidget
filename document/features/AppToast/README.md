Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppToast Usage Guide

**Source Code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppToast.kt  
**This document was created with the assistance of ChatGPT.**

## Overview

`AppToast` is a simple utility for displaying toast messages from anywhere in your application using coroutines.

> Automatically switches to the main thread when showing toasts.

## How to Use

### 1. Show a short-duration toast

```kotlin
AppToast.show("Operation completed successfully")
```

### 2. Show a long-duration toast

```kotlin
AppToast.show("This message will stay longer", Toast.LENGTH_LONG)
```

## Notes

- Automatically ignores null or blank messages.
- Runs safely on the main thread using `CoroutinesHolder.main.launch`.