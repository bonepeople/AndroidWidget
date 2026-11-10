多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# IntentLauncher

## 简介

`IntentLauncher` 简化 Android 的 `startActivityForResult` 流程。通过 `.launch()` 扩展函数启动 `Activity`，并以链式回调或协程挂起的方式处理返回结果。

生命周期监听与 Launcher 注册由内部自动完成，无需手动配置。

## 功能

- 链式 `.onSuccess`、`.onFailure`、`.onResult` 回调
- 支持在协程中调用 `.await()` 挂起等待
- 自动生命周期管理
- 支持并发启动 Activity（容量可配置）

## 使用方式

启动 `Intent` 并处理结果：

```kotlin
val intent = Intent(this, DetailActivity::class.java)

intent.launch()
    .onSuccess { data ->
        // 处理成功结果 (Intent?)
    }
    .onFailure { result ->
        // 处理非 RESULT_OK 结果 (ActivityResult)
    }
    .onResult { result ->
        // 处理所有结果
    }
```

如果协程中的后续逻辑需要等待 Activity 返回，可以在 `launch()` 后调用 `await()` 挂起等待：

```kotlin
Intent(this, DetailActivity::class.java)
    .launch()
    .await()
    .onSuccess { data ->
        // Activity 返回后继续处理成功结果
    }
    .onFailure { result ->
        // Activity 返回后处理非 RESULT_OK 结果
    }
```

`await()` 并非必需，现有调用仍可直接在 `launch()` 后链式处理结果。使用 `await()` 时，它会在结果就绪后返回同一个 `IntentResult`，因此后续链式回调只会在 Activity 返回后执行。取消等待中的协程不会取消已经启动的 Activity。同一个 `IntentResult` 仍可通过回调处理，也可以在其他协程中继续等待。

### 并发启动

默认情况下，每个 Activity 初始化一个 `IntentLauncher` 实例，足以满足大多数场景。

若遇到以下异常：

```
IllegalStateException: The number of simultaneously used IntentLaunchers exceeds the limit.
```

可增大容量：

```kotlin
IntentLauncher.initialCapacity = 2 // 或更高
```

## 注意事项

- 对于 `singleTop`、`singleTask`、`singleInstance` 等启动模式，需手动为 `Intent` 添加相应 flags。
- 仅在并发启动多个 Activity 等待结果时，才需要调整 `initialCapacity`。

## 源码链接

- [IntentExtensions.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/ActivityResultContracts.kt)
- [IntentLauncher.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentLauncher.kt)
- [IntentResult.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentResult.kt)