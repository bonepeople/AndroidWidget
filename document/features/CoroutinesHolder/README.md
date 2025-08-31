Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# CoroutinesHolder

## Introduction

`CoroutinesHolder` provides global singleton coroutine scopes for commonly used `Dispatchers` — `Default`, `Main`, and `IO` — plus name-based serial execution. Use them to launch coroutines without creating scopes manually.

## Use Cases

- Utility functions or library modules that need a shared coroutine scope
- Background work from non-lifecycle contexts (e.g. `AppEvent.postAsync`, `AppToast.show`)
- Ensuring the same resource (cache, database, file) is accessed by only one coroutine at a time

## Features

- Ready-to-use `CoroutineScope` instances for `Default`, `Main`, and `IO` dispatchers
- Each scope has its own independent `SupervisorJob`; a failed child does not cancel siblings in the same scope
- Mutex-based serial execution by name: `runSerial`, `launchSerial`, `asyncSerial`

## Choosing an API

| API | When to use | Dispatcher |
|-----|-------------|------------|
| `runSerial` | Already inside a coroutine | Follows the caller's coroutine context |
| `launchSerial` | No existing coroutine, no return value | Determined by `scope` (defaults to `default`) |
| `asyncSerial` | No existing coroutine, result needed | Determined by `scope` (defaults to `default`) |

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

Serial tasks (same `name` never runs concurrently; different names are independent):

```kotlin
// Inside an existing coroutine; does not switch dispatcher, runs on IO via io.launch
CoroutinesHolder.io.launch {
    CoroutinesHolder.runSerial("cache") {
        updateCache()
    }
}

// Launch without an existing coroutine scope; pass scope = io explicitly
CoroutinesHolder.launchSerial("db", scope = CoroutinesHolder.io) {
    writeToDb()
}

// When a result is needed; call await() to obtain it
val deferred = CoroutinesHolder.asyncSerial("file", scope = CoroutinesHolder.io) {
    readFile()
}
val content = deferred.await()
```

## Notes

- The `name` parameter of `runSerial`, `launchSerial`, and `asyncSerial` is required and must not be blank, or an `IllegalArgumentException` is thrown.
- Use meaningful fixed names (e.g. `"cache"`, `"db"`); do not build names dynamically, or the `Mutex` will remain in memory permanently.
- Serial exclusion applies at the coroutine level only; execution is not bound to a specific physical thread.
- **Do not nest serial calls with the same `name` in the same coroutine.** The underlying `Mutex` is not reentrant and will deadlock.
  The `block` of `launchSerial` / `asyncSerial` already runs inside `runSerial`, so calling the same-name `runSerial` inside the block also deadlocks:

```kotlin
// Wrong — will deadlock
CoroutinesHolder.runSerial("cache") {
    CoroutinesHolder.runSerial("cache") { updateCache() }
}

// Wrong — block is already inside runSerial("db"); another same-name runSerial deadlocks
CoroutinesHolder.launchSerial("db", scope = CoroutinesHolder.io) {
    CoroutinesHolder.runSerial("db") { writeToDb() }
}
```

> Note: Calling another same-name `launchSerial` inside a `launchSerial` block does not deadlock (it starts a new coroutine), but is usually unintended. Awaiting the child via `await()` / `join()` will deadlock.

- `runSerial` does not switch dispatcher; scheduling follows the caller's coroutine context.
- `launchSerial` / `asyncSerial` default to the `default` scope (`Dispatchers.Default`); pass `scope = io` or `scope = main` for IO- or UI-bound work.
- Prefer `lifecycleScope` for UI components that need lifecycle awareness.
- Tasks launched here are not automatically cancelled when an Activity or Fragment is destroyed.

## Source Code

[CoroutinesHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/CoroutinesHolder.kt)