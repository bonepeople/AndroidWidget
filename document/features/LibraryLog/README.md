Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# LibraryLog

## Introduction

`LibraryLog` lets a dependency library record what it is doing, so an app developer can see how that library is running. The library builds a `LibraryLogEvent` on the calling thread and queues it with `AppEvent.postAsync`.

The library only publishes the runtime log. The app subscribes to `LibraryLogEvent` and decides how to present it, such as Logcat, a file, or an upload.

Use `AppLog` for the app's own business logs.

## Usage

### Log

A dependency library uses a tag per module and records runtime activity on the paths that matter.

```kotlin
val log = LibraryLog.tag("Network")
log.debug("request started")
log.error("request failed", exception)
```

`verbose`, `debug`, `info`, `warn`, and `error` each take `message: Any?` and an optional `throwable`. The message is converted to a string before the event is queued. `null` becomes `"null"`.

```kotlin
LibraryLog.defaultLog.info("cache ready")
```

The same tag returns the same instance. `message` keeps only the text passed to the call. The thread name is stored in `threadName`. Every call records the caller on the calling thread: `className`, `methodName`, and `lineNumber`. A missing stack frame uses `UnknownClass`, `UnknownMethod`, and `0`. The default `stackOffset` is 4 for a direct call; increase it when the log call is wrapped.

```kotlin
log.stackOffset = 5
log.debug("with context")
```

### Subscribe

An app developer subscribes to the same `AppEvent` broadcast and receives runtime logs published by dependency libraries. Every active subscriber receives each event. `replay = 0`, so a subscriber misses events posted before it starts collecting.

```kotlin
AppEvent.register(myScope) { event ->
    if (event !is LibraryLogEvent) return@register
    // Present the library runtime log.
}
```

A lifecycle owner can use `AppEvent.register(owner, minState, action)` instead. That subscription follows the owner's lifecycle.

## Rules

- Handle one event at a time inside the callback when order matters. Do not launch a new coroutine for every line.
- A slow subscriber can suspend `AppEvent` delivery and let its unlimited queue grow. That backpressure is accepted.
- Exceptions from the callback follow the supplied scope's policy. The app owns the scope and should choose a `SupervisorJob`, install a `CoroutineExceptionHandler`, or catch exceptions when needed.
- Do not put passwords, tokens, or other private data in a log. `toString()` and stack capture run on the caller.
- Do not call `LibraryLog` unconditionally from a log subscriber. That feedback loop never ends.
- Cancelling the `CoroutineScope` or the `Job` returned by `register` ends that subscription.

## Source Code

- [LibraryLog.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/LibraryLog.kt)
- [LibraryLogEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/LibraryLogEvent.kt)
- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)