Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppEvent

**Link to source code**:
- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)
- [AppEventData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEventData.kt)
- [CommonEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/CommonEvent.kt)

`AppEvent` is a lifecycle-aware global event bus built on `MutableSharedFlow`. Post events from anywhere; subscribe in a `LifecycleOwner` (e.g. `Activity`, `Fragment`). Delivery pauses when the lifecycle drops below the configured minimum state, and the subscription is cancelled on destroy.

> 📄 This documentation was assisted by ChatGPT.

## Usage

### 1. Define events

Implement the `AppEventData` marker interface. Prefer typed `data class` events; use the built-in `CommonEvent` for generic signals.

```kotlin
// Typed event
data class TabChangedEvent(val index: Int) : AppEventData

// Generic signal-only event
CommonEvent("refresh")

// Generic event with payload
CommonEvent("user_login", userId)
```

### 2. Subscribe

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

By default, events are delivered only when the lifecycle is at least `STARTED`. Pass `minState` to receive events earlier:

```kotlin
AppEvent.register(this, minState = Lifecycle.State.CREATED) { event ->
    // ...
}
```

### 3. Post events

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

## API overview

| Method | Description |
|--------|-------------|
| `register(owner, minState, action)` | Lifecycle-bound subscription; `action` is a suspend function |
| `post(event)` | Post an event from a coroutine |
| `postAsync(event)` | Post an event asynchronously from a non-coroutine context |

## Notes

- **Non-sticky**: Events posted before registration are not replayed; only new events after subscribing are received.
- **In-process only**: Event objects are passed by reference within the same process; cross-process delivery is not supported.
- **Lifecycle-safe**: Subscriptions are automatically cancelled when the `Activity` / `Fragment` is destroyed; no manual unregister needed.
- **Buffer policy**: Uses `DROP_OLDEST` overflow handling; under extreme load, older events may be dropped.
- **Type filtering**: Every subscriber receives every posted event; filter with `when`. Typed events are easier to maintain than `CommonEvent`.
