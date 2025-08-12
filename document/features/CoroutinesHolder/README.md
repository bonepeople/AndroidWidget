Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# CoroutinesHolder

## Introduction

`CoroutinesHolder` provides global singleton coroutine scopes for commonly used `Dispatchers` — `Default`, `Main`, and `IO`. Use them to launch coroutines without creating scopes manually.

## Use Cases

- Utility functions or library modules that need a shared coroutine scope
- Background work from non-lifecycle contexts (e.g. `AppEvent.postAsync`, `AppToast.show`)

## Features

- Ready-to-use `CoroutineScope` instances for `Default`, `Main`, and `IO` dispatchers
- Each scope has its own independent `Job`

## Usage

CPU-bound tasks:

```kotlin
CoroutinesHolder.default.launch {
    // Perform heavy computation
}
```

UI-related tasks:

```kotlin
CoroutinesHolder.main.launch {
    // Update UI safely
}
```

IO-bound tasks:

```kotlin
CoroutinesHolder.io.launch {
    // Read/write files, perform network calls
}
```

## Notes

- Prefer `lifecycleScope` for UI components that need lifecycle awareness.
- Tasks launched here are not automatically cancelled when an Activity or Fragment is destroyed.

## Source Code

[CoroutinesHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/CoroutinesHolder.kt)