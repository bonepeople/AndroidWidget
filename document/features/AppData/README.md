Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppData

## Introduction

`AppData` is a key-value storage utility based on Jetpack DataStore, designed to persist configuration data efficiently in Android applications. It supports both asynchronous (suspend) and blocking (synchronous) access, as well as multiple isolated instances by name.

## Use Cases

- Replace `SharedPreferences` with a coroutine-safe, modern solution
- Manage configurations for multiple users or modules
- Monitor preference changes via reactive streams
- Track data versioning across multiple preference stores

## Features

- Read and write basic types (String, Int, Long, Float, Double, Boolean)
- Suspend functions and synchronous APIs
- Reactive access with `Flow`
- Multiple named data store instances
- Automatic version and registry management

## Usage

Use the default data store:

```kotlin
val data = AppData.default
```

Create a custom named store:

```kotlin
val config = AppData.create("settings")
```

Store data (suspend):

```kotlin
runBlocking {
    data.putString("username", "jack")
    data.putInt("launchCount", 5)
}
```

Retrieve data (suspend):

```kotlin
val username = data.getString("username", "guest")
```

Store/retrieve data (synchronous):

```kotlin
data.putBooleanSync("night_mode", true)
val enabled = data.getBooleanSync("night_mode", false)
```

Observe changes with `Flow`:

```kotlin
data.getIntFlow("launchCount").collect {
    Log.d("Launch Count", "$it")
}
```

## Notes

- `storeName` must not contain illegal characters (`<>:"/\|?*`) or end with a period or space.
- Synchronous APIs use `runBlocking` — avoid calling them on the main thread.

## Source Code

[AppData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppData.kt)