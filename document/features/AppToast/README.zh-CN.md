多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppToast 使用指南

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppToast.kt  
**本文档由 ChatGPT 协助完成。**

## 简介

`AppToast` 是一个便捷的 Toast 工具类，支持在全局任意位置显示提示消息，并自动切换至主线程执行。

> 适用于在任意协程或线程中调用，无需手动切换至主线程。

## 使用方法

### 1. 显示短时间的 Toast

```kotlin
AppToast.show("操作已完成")
```

### 2. 显示长时间的 Toast

```kotlin
AppToast.show("该提示会停留较长时间", Toast.LENGTH_LONG)
```

## 注意事项

- 会自动忽略 null 或空字符串。
- 内部使用 `CoroutinesHolder.main.launch` 保证主线程安全。