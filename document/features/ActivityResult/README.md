Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# IntentLauncher

## Introduction

`IntentLauncher` simplifies the Android `startActivityForResult` workflow. Launch an `Activity` with the `.launch()` extension and handle results through chainable callbacks or coroutine suspension.

Lifecycle monitoring and launcher registration are handled automatically—no manual setup is required.

## Features

- Chainable `.onSuccess`, `.onFailure`, and `.onResult` handlers
- Suspending `.await()` support for coroutines
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

If subsequent coroutine logic needs to wait for the Activity to return, call `await()` after `launch()` to suspend until the result is available:

```kotlin
Intent(this, DetailActivity::class.java)
    .launch()
    .await()
    .onSuccess { data ->
        // Continue with the successful result after the Activity returns
    }
    .onFailure { result ->
        // Handle a non-RESULT_OK result after the Activity returns
    }
```

`await()` is optional; existing call sites can continue chaining handlers directly from `launch()`. When used, it returns the same `IntentResult` after its result is available, so handlers chained after it run only after the Activity returns. Cancelling the waiting coroutine does not cancel the launched Activity. The same `IntentResult` can still be handled through callbacks or awaited from another coroutine.

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