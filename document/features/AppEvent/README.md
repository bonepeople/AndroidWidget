Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppEvent

## Introduction

`AppEvent` is a lifecycle-aware global event bus built on `MutableSharedFlow`. Post events from anywhere; subscribe in a `LifecycleOwner` (e.g. `Activity`, `Fragment`).

**Posting is independent of subscriber lifecycle** — `post` / `postAsync` always emit to the global flow. **Receiving is gated by lifecycle** — a subscriber collects events only when its lifecycle is at least `minState`; when it drops below, that subscriber stops receiving and events emitted during the inactive period are not replayed when the lifecycle resumes. The subscription is cancelled on destroy.

## Features

- Non-sticky event delivery
- Typed `data class` events or generic `CommonEvent` signals
- `post` (coroutine) and `postAsync` (non-coroutine) posting modes
- Automatic subscription cleanup on lifecycle destroy

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

`postAsync` dispatches via [CoroutinesHolder](../CoroutinesHolder/README.md) `default` scope on a background thread; subscriber callbacks still run on the main thread via `lifecycleScope`.

### API overview

| Method | Description |
|--------|-------------|
| `register(owner, minState, action)` | Lifecycle-bound subscription; `action` is a suspend function |
| `post(event)` | Post an event from a coroutine |
| `postAsync(event)` | Post an event asynchronously from a non-coroutine context |

## Notes

- **Non-sticky**: Events posted before registration are not replayed. Events posted while a subscriber is inactive (lifecycle below `minState`) are also not replayed when the lifecycle resumes — only newly posted events are received.
- **Posting vs receiving**: Senders are unaffected by any subscriber's lifecycle. Other subscribers that remain active still receive events normally.
- **In-process only**: Event objects are passed by reference within the same process; cross-process delivery is not supported.
- **Lifecycle-safe**: Subscriptions are automatically cancelled when the `Activity` / `Fragment` is destroyed; no manual unregister needed.
- **Buffer policy**: Uses `DROP_OLDEST` overflow handling; under extreme load, older events may be dropped.
- **Type filtering**: Every subscriber receives every posted event; filter with `when`. Typed events are easier to maintain than `CommonEvent`.

## Source Code

- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)
- [AppEventData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEventData.kt)
- [CommonEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/CommonEvent.kt)