多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# CoroutinesHolder

## 简介

`CoroutinesHolder` 提供 `Default`、`Main`、`IO` 三种常用 `Dispatcher` 的全局单例协程作用域，无需手动创建 Scope 即可启动协程；同时提供按名称互斥的串行执行能力。

## 场景

- 工具函数或库模块中需要共享协程作用域
- 从无生命周期上下文中执行后台任务（如 `AppEvent.postAsync`、`AppToast.show`）
- 需要保证相同资源（如缓存、数据库、文件）在同一时刻只被一个协程访问

## 功能

- 开箱即用的 `Default`、`Main`、`IO` 协程作用域
- 每个作用域拥有独立的 `SupervisorJob`，单个协程失败不会取消同作用域内的其他协程
- 按名称 Mutex 串行执行：`runSerial`、`launchSerial`、`asyncSerial`

## API 选择

| API | 调用场景 | 调度线程 |
|-----|----------|----------|
| `runSerial` | 已在协程内 | 由调用方协程上下文决定 |
| `launchSerial` | 无现有协程，无需返回值 | 由 `scope` 参数决定（默认 `default`） |
| `asyncSerial` | 无现有协程，需要返回值 | 由 `scope` 参数决定（默认 `default`） |

## 使用方式

CPU 密集型任务：

```kotlin
CoroutinesHolder.default.launch {
    // 执行耗时计算
}
```

UI 相关任务：

```kotlin
CoroutinesHolder.main.launch {
    // 安全更新 UI
}
```

IO 密集型任务：

```kotlin
CoroutinesHolder.io.launch {
    // 读写文件、网络请求
}
```

串行任务（相同 `name` 不会并发执行，不同 `name` 互不影响）：

```kotlin
// 已在协程内；不切换 Dispatcher，跟随 io.launch 运行在 IO 线程
CoroutinesHolder.io.launch {
    CoroutinesHolder.runSerial("cache") {
        updateCache()
    }
}

// 无现有协程作用域时启动；显式指定 scope = io
CoroutinesHolder.launchSerial("db", scope = CoroutinesHolder.io) {
    writeToDb()
}

// 需要返回值时；调用 await() 获取结果
val deferred = CoroutinesHolder.asyncSerial("file", scope = CoroutinesHolder.io) {
    readFile()
}
val content = deferred.await()
```

## 注意事项

- `runSerial`、`launchSerial`、`asyncSerial` 的 `name` 参数必填，且不能为空字符串，否则抛出 `IllegalArgumentException`。
- 请使用语义明确的固定名称（如 `"cache"`、`"db"`），不要使用动态拼接的名称，否则 `Mutex` 会永久驻留内存。
- 串行互斥仅在协程层面生效，不绑定固定物理线程。
- **禁止在同一协程内嵌套调用相同 `name` 的串行方法**，底层 `Mutex` 不可重入，会导致死锁。
  `launchSerial` / `asyncSerial` 的 `block` 已在内部通过 `runSerial` 持有锁，因此在 `block` 内再次调用同名 `runSerial` 也会死锁：

```kotlin
// 错误示例：会死锁
CoroutinesHolder.runSerial("cache") {
    CoroutinesHolder.runSerial("cache") { updateCache() }
}

// 错误示例：block 已在 runSerial("db") 内，内部再次 runSerial 同名会死锁
CoroutinesHolder.launchSerial("db", scope = CoroutinesHolder.io) {
    CoroutinesHolder.runSerial("db") { writeToDb() }
}
```

> 注意：在 `launchSerial` 的 `block` 内调用另一个同名 `launchSerial` 不会死锁（会启动新协程），但通常不是预期用法；若对返回值调用 `await()` / `join()` 等待子任务，则会死锁。

- `runSerial` 不切换 Dispatcher，调度由调用方协程上下文决定。
- `launchSerial` / `asyncSerial` 默认使用 `default` 作用域（`Dispatchers.Default`），IO 或 UI 任务请显式传入 `scope = io` / `scope = main`。
- 需要生命周期感知的 UI 组件应优先使用 `lifecycleScope`。
- 此处启动的任务不会随 Activity 或 Fragment 销毁而自动取消。

## 源码链接

[CoroutinesHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/CoroutinesHolder.kt)