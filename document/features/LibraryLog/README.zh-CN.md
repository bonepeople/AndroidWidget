多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# LibraryLog

## 简介

`LibraryLog` 供依赖库记录自身运行情况，便于 App 开发者了解依赖库正在执行的工作。依赖库在调用线程构建 `LibraryLogEvent`，并通过 `AppEvent.postAsync` 将其加入队列。

依赖库只负责发布运行日志。App 订阅 `LibraryLogEvent`，并自行决定输出方式，例如 Logcat、文件或上传服务。

App 自身的业务日志应使用 `AppLog`。

## 使用方式

### 记录日志

依赖库可以按模块使用不同 tag，并在关键执行路径中记录运行情况。

```kotlin
val log = LibraryLog.tag("Network")
log.debug("request started")
log.error("request failed", exception)
```

`verbose`、`debug`、`info`、`warn` 和 `error` 均接收 `message: Any?` 及可选的 `throwable`。消息会在事件入队前转换为字符串，`null` 转换为 `"null"`。

```kotlin
LibraryLog.defaultLog.info("cache ready")
```

相同 tag 始终返回同一实例。`message` 仅保留调用时传入的文本，线程名称保存在 `threadName`。每次调用都会在调用线程记录调用位置：`className`、`methodName` 和 `lineNumber`。无法取得调用栈时分别使用 `UnknownClass`、`UnknownMethod` 和 `0`。直接调用时默认 `stackOffset` 为 4；经过封装调用时应相应增加。

```kotlin
log.stackOffset = 5
log.debug("with context")
```

### 订阅

App 开发者订阅同一个 `AppEvent` 广播，即可接收依赖库发布的运行日志。每个活跃订阅者都会收到事件。由于 `replay = 0`，订阅开始前发布的事件不会补发。

```kotlin
AppEvent.register(myScope) { event ->
    if (event !is LibraryLogEvent) return@register
    // 展示依赖库运行日志
}
```

也可以使用 `AppEvent.register(owner, minState, action)` 创建生命周期订阅；该订阅会跟随 `LifecycleOwner` 的生命周期。

## 规则

- 顺序很重要时，应在回调中逐条处理事件，不要为每条日志启动新的协程。
- 慢订阅者可能挂起 `AppEvent` 分发，使无限队列持续增长；该背压行为已被接受。
- 回调异常遵循传入作用域的策略。App 拥有该作用域，应按需选择 `SupervisorJob`、配置 `CoroutineExceptionHandler` 或自行捕获异常。
- 不要在日志中写入密码、令牌或其他隐私数据。`toString()` 和调用栈捕获均在调用线程执行。
- 不要在日志订阅者中无条件调用 `LibraryLog`，否则会形成无法结束的反馈循环。
- 取消 `CoroutineScope` 或 `register` 返回的 `Job` 即可结束对应订阅。

## 源码链接

- [LibraryLog.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/LibraryLog.kt)
- [LibraryLogEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/LibraryLogEvent.kt)
- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)