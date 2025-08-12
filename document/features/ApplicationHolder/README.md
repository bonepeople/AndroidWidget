Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# ApplicationHolder

## Introduction

`ApplicationHolder` provides centralized access to the `Application` instance and key application-level information such as debug status, version, and package name.

It is automatically initialized via Jetpack Startup, so no manual setup is required.

## Features

- Access the global `Application` context
- Check whether the app is running in debug mode
- Read version name, version code, and package name

## Usage

After app startup, use it from anywhere in your application:

```kotlin
val context = ApplicationHolder.app
val isDebug = ApplicationHolder.debug
val versionName = ApplicationHolder.getVersionName()
val versionCode = ApplicationHolder.getVersionCode()
val packageName = ApplicationHolder.getPackageName()
```

Typical output:

- `debug`: `true` if the app is built in debug mode
- `getVersionName()`: e.g. `"1.0.0"`
- `getVersionCode()`: e.g. `10000`

## Notes

- Accessing `ApplicationHolder` before initialization throws an exception.

## Source Code

[ApplicationHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ApplicationHolder.kt)