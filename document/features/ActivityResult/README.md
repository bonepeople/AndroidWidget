Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# IntentLauncher

## Introduction

`IntentLauncher` simplifies the Android `startActivityForResult` workflow. Launch an `Activity` with the `.launch()` extension and handle results through chainable callbacks.

Lifecycle monitoring and launcher registration are handled automatically—no manual setup is required.

## Features

- Chainable `.onSuccess`, `.onFailure`, and `.onResult` handlers
- Automatic lifecycle management
- Supports concurrent activity launches (with configurable capacity)

## Usage

Launch an `Intent` and handle the result:

```kotlin
val intent = Intent(this, DetailActivity::class.java)

intent.launch()
    .onSuccess { data ->
        // Handle successful result (Intent?)
    }
    .onFailure { result ->
        // Handle non-RESULT_OK result (ActivityResult)
    }
    .onResult { result ->
        // Handle all result cases
    }
```

### Concurrent launches

By default, each Activity initializes a single `IntentLauncher` instance, which is sufficient for most use cases.

If you encounter:

```
IllegalStateException: The number of simultaneously used IntentLaunchers exceeds the limit.
```

Increase the capacity:

```kotlin
IntentLauncher.initialCapacity = 2 // or higher
```

## Notes

- For launch modes such as `singleTop`, `singleTask`, or `singleInstance`, add appropriate flags to your `Intent` manually.
- Adjust `initialCapacity` only when launching multiple activities for result concurrently.

## Source Code

- [IntentExtensions.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/ActivityResultContracts.kt)
- [IntentLauncher.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentLauncher.kt)
- [IntentResult.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentResult.kt)