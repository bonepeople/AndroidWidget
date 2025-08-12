多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# CoroutinesHolder

## 简介

`CoroutinesHolder` 提供 `Default`、`Main`、`IO` 三种常用 `Dispatcher` 的全局单例协程作用域，无需手动创建 Scope 即可启动协程。

## 场景

- 工具函数或库模块中需要共享协程作用域
- 从无生命周期上下文中执行后台任务（如 `AppEvent.postAsync`、`AppToast.show`）

## 功能

- 开箱即用的 `Default`、`Main`、`IO` 协程作用域
- 每个作用域拥有独立的 `Job`

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

## 注意事项

- 需要生命周期感知的 UI 组件应优先使用 `lifecycleScope`。
- 此处启动的任务不会随 Activity 或 Fragment 销毁而自动取消。

## 源码链接

[CoroutinesHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/CoroutinesHolder.kt)