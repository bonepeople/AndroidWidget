Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppEvent

## Introduction

`AppEvent` is a global event bus built on an unlimited `Channel` and a `MutableSharedFlow`. Post events from anywhere; subscribe in a `LifecycleOwner` (e.g. `Activity`, `Fragment`) or in any `CoroutineScope`.

**Posting is independent of subscriber lifecycle** — `post` / `postAsync` always enqueue events for the global flow. **Lifecycle receiving is gated by `minState`** — a lifecycle subscriber stops receiving below that state, and events emitted during the inactive period are not replayed when the lifecycle resumes. A scope subscriber remains active until its scope or returned `Job` is cancelled.

## Features

- Non-sticky event delivery
- Typed `data class` events or generic `CommonEvent` signals
- `post` (coroutine) and `postAsync` (non-coroutine) posting modes
- Automatic subscription cleanup on lifecycle destroy
- `CoroutineScope` subscription for work that must stay active in the background

## Usage

### Define events

Implement the `AppEventData` marker interface. Prefer typed `data class` events; use `CommonEvent` for generic signals.

```kotlin
// Typed event
data class TabChangedEvent(val index: Int) : AppEventData

// Generic signal-only event
CommonEvent("refresh")

// Generic event with payload
CommonEvent("user_login", userId)
```

### Subscribe

Call `register` on a `LifecycleOwner` and handle events with `when`:

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppEvent.register(this) { event ->
            when (event) {
                is TabChangedEvent -> switchTab(event.index)
                is CommonEvent -> {
                    when (event.name) {
                        "refresh" -> reloadData()
                        "user_login" -> onUserLogin(event.data)
                    }
                }
            }
        }
    }
}
```

By default, a subscriber receives events only when the lifecycle is at least `STARTED`. Pass `minState` to receive events earlier:

```kotlin
AppEvent.register(this, minState = Lifecycle.State.CREATED) { event ->
    // ...
}
```

Subscribe without a lifecycle when the collector must keep running in the background. Collection starts before `register` returns. Cancel the scope, or the returned `Job`, to stop:

```kotlin
val job = AppEvent.register(myScope) { event ->
    if (event is CommonEvent && event.name == "refresh") {
        reloadData()
    }
}
```

`LibraryLogEvent` is a runtime log published by a dependency library. See [LibraryLog](../LibraryLog/README.md).

### Post events

Use `post` inside a coroutine; use `postAsync` from non-coroutine contexts (callbacks, click listeners, etc.):

```kotlin
// Inside a coroutine
lifecycleScope.launch {
    AppEvent.post(TabChangedEvent(0))
}

// From any thread / non-coroutine context
AppEvent.postAsync(TabChangedEvent(0))
AppEvent.postAsync(CommonEvent("refresh"))
```

`postAsync` only calls `Channel.trySend` and returns after the enqueue attempt. It does not wait until subscribers finish. One consumer on [CoroutinesHolder](../CoroutinesHolder/README.md) `default` then emits to the shared flow. Lifecycle subscribers collect on the main thread via `lifecycleScope`. A scope subscriber collects on the dispatcher of the scope it passed in.

### API overview

| Method | Description |
|--------|-------------|
| `register(owner, minState, action)` | Lifecycle-bound subscription; `action` is a suspend function |
| `register(scope, action)` | Scope-bound subscription. Returns a `Job`. Collection starts before the call returns |
| `post(event)` | Post an event from a coroutine |
| `postAsync(event)` | Enqueue an event from a non-coroutine context. Returns after `trySend`, not after subscribers finish |

## Notes

- **Non-sticky**: Events posted before registration are not replayed. Events posted while a subscriber is inactive (lifecycle below `minState`) are also not replayed when the lifecycle resumes — only newly posted events are received.
- **Posting vs receiving**: Senders are unaffected by any subscriber's lifecycle. Lifecycle subscribers receive only while active; scope subscribers receive until cancelled.
- **In-process only**: Event objects are passed by reference within the same process; cross-process delivery is not supported.
- **Subscription lifetime**: A lifecycle subscription is cancelled when its `Activity` / `Fragment` is destroyed. A scope subscription ends when its `CoroutineScope` or returned `Job` is cancelled.
- **Scope exception policy**: Exceptions from a scope subscriber follow the propagation and handling policy of the supplied scope. The app is responsible for choosing a `SupervisorJob`, installing a `CoroutineExceptionHandler`, or catching exceptions when needed.
- **Queue and buffer**: The queue is `Channel.UNLIMITED`. The shared flow uses `replay = 0`, `extraBufferCapacity = 64`, and `onBufferOverflow = SUSPEND`. A slow subscriber can suspend the single consumer and let the unlimited queue grow. Events are not dropped. This backpressure is intentional.
- **Order**: Subscribers that need the enqueue order should handle each event directly in the callback. Do not launch a new coroutine per event.
- **Type filtering**: Every subscriber receives every posted event; filter with `when`. Typed events are easier to maintain than `CommonEvent`.

## Source Code

- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)
- [AppEventData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEventData.kt)
- [CommonEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/CommonEvent.kt)