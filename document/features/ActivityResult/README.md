Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# IntentLauncher Usage Guide

This document was generated with the help of ChatGPT.

## Overview

`IntentLauncher` is a utility designed to simplify the Android `startActivityForResult` workflow. It allows developers to launch an `Activity` using the `.launch()` extension function and handle the result in a fluent, reactive way.

## How to Use

### 1. Setup

No manual registration is required. Lifecycle monitoring and launcher registration are handled automatically by the internal module.

### 2. Launch an Activity and Handle Result

You can directly launch an `Intent` using `.launch()` and chain result handlers:

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

## Concurrency (Optional Configuration)

By default, each Activity initializes a single `IntentLauncher` instance, which is sufficient for most use cases.

If you encounter the following exception:

```
IllegalStateException: The number of simultaneously used IntentLaunchers exceeds the limit.
```

Then you may increase the capacity:

```kotlin
IntentLauncher.initialCapacity = 2 // or higher
```

## Notes

- For launch modes like `singleTop`, `singleTask`, or `singleInstance`, be sure to manually add appropriate flags to your `Intent`.
- You only need to adjust `initialCapacity` when launching multiple activities for result concurrently.

## Source Code Links

- [IntentExtensions.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/ActivityResultContracts.kt)
- [IntentLauncher.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentLauncher.kt)
- [IntentResult.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentResult.kt)
