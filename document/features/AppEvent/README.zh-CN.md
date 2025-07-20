多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppEvent

**源代码链接**：
- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)
- [AppEventData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEventData.kt)
- [CommonEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/CommonEvent.kt)

`AppEvent` 是基于 `MutableSharedFlow` 的全局事件总线，支持生命周期感知。可在任意位置发送事件，在 `Activity`、`Fragment` 等 `LifecycleOwner` 中订阅；当生命周期低于指定状态时暂停分发，销毁时自动取消订阅。

> 📄 本文档由 ChatGPT 协助完成。

## 使用方法

### 1. 定义事件

实现 `AppEventData` 标记接口即可。推荐用 `data class` 定义强类型事件；也可直接使用内置的 `CommonEvent` 传递通用信号。

```kotlin
// 强类型事件
data class TabChangedEvent(val index: Int) : AppEventData

// 通用事件（仅信号，无附加数据）
CommonEvent("refresh")

// 通用事件（带 payload）
CommonEvent("user_login", userId)
```

### 2. 订阅事件

在 `LifecycleOwner` 中调用 `register`，用 `when` 处理关心的事件类型：

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

默认仅在生命周期达到 `STARTED` 及以上时分发事件。若需在后台也接收，可传入 `minState`：

```kotlin
AppEvent.register(this, minState = Lifecycle.State.CREATED) { event ->
    // ...
}
```

### 3. 发送事件

在协程中调用 `post`；在非协程上下文（如普通回调、点击监听）中调用 `postAsync`：

```kotlin
// 协程内
lifecycleScope.launch {
    AppEvent.post(TabChangedEvent(0))
}

// 任意线程 / 非协程上下文
AppEvent.postAsync(TabChangedEvent(0))
AppEvent.postAsync(CommonEvent("refresh"))
```

`postAsync` 会通过 [CoroutinesHolder](../CoroutinesHolder/README.zh-CN.md) 的 `default` 作用域在后台线程发送；订阅回调仍由 `lifecycleScope` 在主线程执行。

## API 概览

| 方法 | 说明 |
|------|------|
| `register(owner, minState, action)` | 绑定生命周期订阅事件；`action` 为挂起函数 |
| `post(event)` | 在协程中发送事件 |
| `postAsync(event)` | 在非协程上下文中异步发送事件 |

## 注意事项

- **非粘性**：订阅前已发送的事件不会被补发，仅接收注册之后的新事件。
- **进程内通信**：事件对象在同一进程内按引用传递，不支持跨进程。
- **生命周期安全**：`Activity` / `Fragment` 销毁后订阅自动取消，无需手动反注册。
- **缓冲策略**：内部使用 `DROP_OLDEST` 溢出策略，极端情况下旧事件可能被丢弃。
- **类型分发**：所有订阅者都会收到每次 `post` 的事件，请在 `when` 中过滤所需类型；强类型事件比 `CommonEvent` 更易维护。
