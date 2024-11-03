多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# IntentLauncher 使用说明

本文档由 ChatGPT 协助完成。

## 简介

`IntentLauncher` 是一个用于简化 Android 中 `startActivityForResult` 流程的工具。它允许开发者使用扩展函数 `Intent.launch()` 以更简洁的方式启动 `Activity`，并以响应式链式方式处理返回结果。

## 如何使用

### 1. 准备工作

不需要手动调用任何注册代码，`IntentLauncher` 会通过内部模块自动完成生命周期监听的注册工作。

### 2. 启动一个 `Activity` 并处理返回结果

你可以直接对一个 `Intent` 调用 `.launch()` 方法并链式处理返回结果：

```kotlin
val intent = Intent(this, DetailActivity::class.java)

intent.launch()
    .onSuccess { data ->
        // 成功返回，处理 data（Intent?）
    }
    .onFailure { result ->
        // 非 RESULT_OK 返回，处理 result（ActivityResult）
    }
    .onResult { result ->
        // 所有情况的最终处理
    }
```

## 并发处理说明（可选设置）

默认情况下每个 Activity 只初始化一个 `IntentLauncher` 实例，这对于大多数场景足够。如果你遇到如下异常：

```
IllegalStateException: The number of simultaneously used IntentLaunchers exceeds the limit.
```

则可以通过设置以下参数来增加容量：

```kotlin
IntentLauncher.initialCapacity = 2 // 或更高
```

## 注意事项

- 若启动模式为 `singleTop` / `singleTask` / `singleInstance`，建议在 `Intent` 上手动设置正确的 flag。
- 仅在确实需要同时发起多个 startActivityForResult 操作时，考虑调整 `initialCapacity`。

## 源代码链接

- [IntentExtensions.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/ActivityResultContracts.kt)
- [IntentLauncher.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentLauncher.kt)
- [IntentResult.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/activity/result/IntentResult.kt)
