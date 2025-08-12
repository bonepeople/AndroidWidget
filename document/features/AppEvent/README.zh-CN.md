多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppEvent

## 简介

`AppEvent` 是基于 `MutableSharedFlow` 的生命周期感知全局事件总线。可在任意位置发送事件，在 `LifecycleOwner`（如 `Activity`、`Fragment`）中订阅。

**发送与订阅方的生命周期无关** — `post` / `postAsync` 始终向全局 Flow 写入事件。**接收受生命周期约束** — 订阅者仅在其生命周期不低于 `minState` 时收集事件；低于该状态时停止接收，此期间发出的事件不会在生命周期恢复后补发。销毁时自动取消订阅。

## 功能

- 非粘性事件分发
- 强类型 `data class` 事件或通用 `CommonEvent` 信号
- `post`（协程）和 `postAsync`（非协程）两种发送模式
- 生命周期销毁时自动清理订阅

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

`postAsync` 通过 [CoroutinesHolder](../CoroutinesHolder/README.zh-CN.md) 的 `default` 作用域在后台线程分发；订阅者回调仍通过 `lifecycleScope` 在主线程执行。

### API 概览

| 方法 | 说明 |
|------|------|
| `register(owner, minState, action)` | 生命周期绑定订阅；`action` 为 suspend 函数 |
| `post(event)` | 在协程中发送事件 |
| `postAsync(event)` | 在非协程上下文中异步发送事件 |

## 注意事项

- **非粘性**：注册前发送的事件不会重放。订阅者处于 inactive 状态（生命周期低于 `minState`）期间发送的事件，恢复后也不会补发，仅接收恢复后新发送的事件。
- **发送与接收分离**：发送方不受任何订阅方生命周期影响；其他仍处于 active 状态的订阅者照常接收。
- **进程内**：事件对象在同一进程内按引用传递，不支持跨进程。
- **生命周期安全**：`Activity` / `Fragment` 销毁时自动取消订阅，无需手动注销。
- **缓冲策略**：使用 `DROP_OLDEST` 溢出处理；极端负载下可能丢弃较早事件。
- **类型过滤**：每个订阅者都会收到所有已发送事件，需用 `when` 过滤。强类型事件比 `CommonEvent` 更易维护。

## 源码链接

- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)
- [AppEventData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEventData.kt)
- [CommonEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/CommonEvent.kt)