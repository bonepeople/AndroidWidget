多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# CoroutinesHolder

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/CoroutinesHolder.kt

`CoroutinesHolder` 提供全局的协程作用域单例对象，涵盖常用的 `Dispatchers` 类型：`Default`、`Main` 和 `IO`，方便在应用中快捷启动协程任务。

> 📄 本文档由 ChatGPT 协助完成。

## 使用方法

根据任务类型选择对应的协程作用域：

### CPU 密集型任务（Default Dispatcher）：

```kotlin
CoroutinesHolder.default.launch {
    // 执行复杂计算
}
```

### UI 相关任务（Main Dispatcher）：

```kotlin
CoroutinesHolder.main.launch {
    // 安全地操作界面
}
```

### IO 密集型任务（IO Dispatcher）：

```kotlin
CoroutinesHolder.io.launch {
    // 执行网络请求、文件读写等
}
```

每个作用域自带独立的 `Job`，可以独立取消（但此对象本身不暴露取消机制）。

## 注意事项

- 非常适合工具类、库模块等场景，避免重复创建多个协程作用域。
- 如果任务需要与生命周期绑定，请优先使用如 `lifecycleScope` 等具备生命周期感知的作用域。