Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppToast

## Introduction

`AppToast` displays toast messages from anywhere in your application. It automatically switches to the main thread and ignores blank messages.

## Features

- Short and long duration support
- Thread-safe display via coroutines
- Automatic blank message filtering

## Usage

Show a short-duration toast:

```kotlin
AppToast.show("Operation completed successfully")
```

Show a long-duration toast:

```kotlin
AppToast.show("This message will stay longer", Toast.LENGTH_LONG)
```

## Notes

- Runs on the main thread using `CoroutinesHolder.main.launch`.

## Source Code

[AppToast.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppToast.kt)