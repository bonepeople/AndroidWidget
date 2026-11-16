多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppEvent

## 简介

`AppEvent` 是基于无限 `Channel` 和 `MutableSharedFlow` 的全局事件总线。可在任意位置发送事件，在 `LifecycleOwner`（如 `Activity`、`Fragment`）或任意 `CoroutineScope` 中订阅。

**发送与订阅方的生命周期无关** — `post` / `postAsync` 始终将事件加入全局 Flow 的队列。**生命周期订阅受 `minState` 约束** — 生命周期低于该状态时停止接收，此期间发出的事件不会在生命周期恢复后补发。Scope 订阅保持活跃，直到其作用域或返回的 `Job` 被取消。

## 功能

- 非粘性事件分发
- 强类型 `data class` 事件或通用 `CommonEvent` 信号
- `post`（协程）和 `postAsync`（非协程）两种发送模式
- 生命周期销毁时自动清理订阅
- 不依赖界面生命周期的 `CoroutineScope` 订阅

## 使用方式

### 定义事件

实现 `AppEventData` 标记接口。优先使用强类型 `data class`；通用信号可使用 `CommonEvent`。

```kotlin
// 强类型事件
data class TabChangedEvent(val index: Int) : AppEventData

// 纯信号事件
CommonEvent("refresh")

// 带载荷的通用事件
CommonEvent("user_login", userId)
```

### 订阅

在 `LifecycleOwner` 上调用 `register`，用 `when` 处理事件：

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

默认情况下，订阅者仅在其生命周期至少为 `STARTED` 时接收事件。传入 `minState` 可更早接收：

```kotlin
AppEvent.register(this, minState = Lifecycle.State.CREATED) { event ->
    // ...
}
```

需要在后台保持收集时，可以使用不绑定生命周期的订阅。`register` 返回前已经开始收集；取消作用域或返回的 `Job` 即可停止：

```kotlin
val job = AppEvent.register(myScope) { event ->
    if (event is CommonEvent && event.name == "refresh") {
        reloadData()
    }
}
```

`LibraryLogEvent` 是依赖库发布的运行日志，详见 [LibraryLog](../LibraryLog/README.zh-CN.md)。

### 发送事件

在协程中使用 `post`；在回调、点击监听等非协程上下文中使用 `postAsync`：

```kotlin
// 协程内
lifecycleScope.launch {
    AppEvent.post(TabChangedEvent(0))
}

// 任意线程 / 非协程上下文
AppEvent.postAsync(TabChangedEvent(0))
AppEvent.postAsync(CommonEvent("refresh"))
```

`postAsync` 只调用 `Channel.trySend`，入队尝试结束后立即返回，不会等待订阅者处理完成。随后由 [CoroutinesHolder](../CoroutinesHolder/README.zh-CN.md) `default` 上的单个消费者写入共享 Flow。生命周期订阅者仍通过 `lifecycleScope` 在主线程收集；scope 订阅者在传入作用域的调度器上收集。

### API 概览

| 方法 | 说明 |
|------|------|
| `register(owner, minState, action)` | 生命周期绑定订阅；`action` 为 suspend 函数 |
| `register(scope, action)` | 绑定 `CoroutineScope` 的订阅，返回 `Job`。调用返回前已开始收集 |
| `post(event)` | 在协程中发送事件 |
| `postAsync(event)` | 在非协程上下文中入队。`trySend` 结束后返回，不等待订阅者处理完成 |

## 注意事项

- **非粘性**：注册前发送的事件不会重放。订阅者处于 inactive 状态（生命周期低于 `minState`）期间发送的事件，恢复后也不会补发，仅接收恢复后新发送的事件。
- **发送与接收分离**：发送方不受任何订阅方生命周期影响。生命周期订阅者仅在 active 状态下接收；scope 订阅者持续接收直到被取消。
- **进程内**：事件对象在同一进程内按引用传递，不支持跨进程。
- **订阅生命周期**：`Activity` / `Fragment` 销毁时自动取消生命周期订阅。Scope 订阅在其 `CoroutineScope` 或返回的 `Job` 被取消时结束。
- **Scope 异常策略**：scope 订阅的回调异常遵循传入作用域的传播与处理策略。App 应按需选择 `SupervisorJob`、配置 `CoroutineExceptionHandler` 或自行捕获异常。
- **队列与缓冲**：队列为 `Channel.UNLIMITED`。共享 Flow 为 `replay = 0`、`extraBufferCapacity = 64`、`onBufferOverflow = SUSPEND`。慢订阅者会挂起唯一消费者，无限队列因此增长，事件不会被丢弃。该背压是既有行为，本次予以保留。
- **顺序**：需要按入队顺序处理时，应在回调里直接串行执行，不要为每条事件再启动新协程。
- **类型过滤**：每个订阅者都会收到所有已发送事件，需用 `when` 过滤。强类型事件比 `CommonEvent` 更易维护。

## 源码链接

- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)
- [AppEventData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEventData.kt)
- [CommonEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/CommonEvent.kt)