Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppData Usage Guide

> This document was generated with the help of ChatGPT  
> Source code link: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppData.kt

## Introduction

`AppData` is a key-value data storage utility class based on Jetpack DataStore, designed to persist configuration data efficiently in Android applications. It supports both asynchronous (suspend) and blocking (synchronous) access, as well as multiple isolated instances by name.

## Features

- Read and write for basic types (String, Int, Long, Float, Double, Boolean);
- Offers suspend functions and synchronous APIs;
- Supports reactive access with Flow;
- Allows creation of multiple named data stores;
- Maintains version and registry info automatically.

## How to Use

### 1. Use the default data store

```kotlin
val data = AppData.default
```

### 2. Create a custom named store

```kotlin
val config = AppData.create("settings")
```

### 3. Store data (suspend)

```kotlin
runBlocking {
    data.putString("username", "jack")
    data.putInt("launchCount", 5)
}
```

### 4. Retrieve data (suspend)

```kotlin
val username = data.getString("username", "guest")
```

### 5. Store/retrieve data (synchronous)

```kotlin
data.putBooleanSync("night_mode", true)
val enabled = data.getBooleanSync("night_mode", false)
```

### 6. Observe changes with Flow

```kotlin
data.getIntFlow("launchCount").collect {
    Log.d("Launch Count", "$it")
}
```

## Recommended Use Cases

- Replace `SharedPreferences` with a coroutine-safe and modern solution;
- Manage configurations for multiple users or modules;
- Use reactive streams to monitor preference changes;
- Track data versioning and maintain multiple preference tables.

## Notes

- `storeName` must not contain illegal characters (`<>:"/\\|?*`) or end with a period or space;
- Synchronous APIs use `runBlocking`, avoid using on the main thread;