多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppView 使用指南

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppView.kt  
**本文档由 ChatGPT 协助完成。**

## 简介

`AppView` 是专为 Android `View` 提供的扩展工具类，包含防重复点击、可见性控制、自定义数据存储和测量辅助功能等。

## 功能说明

### 1. 防止重复点击

```kotlin
view.singleClick {
    // 点击事件，只触发一次
}
```

可自定义间隔时间（单位毫秒）：

```kotlin
view.singleClick(1000L) {
    // 1秒内只触发一次
}
```

### 2. 控制 View 可见性

```kotlin
view.show()       // 设置为 VISIBLE
view.hide()       // 设置为 INVISIBLE
view.gone()       // 设置为 GONE
view.switchShow(true)     // 根据布尔值显示或隐藏（GONE）
view.switchVisible(false) // 根据布尔值显示或隐藏（INVISIBLE）
```

### 3. View 内部存储自定义数据

```kotlin
view.putExtra("key", 123)
val value: Int = view.getExtra("key", 0)
view.removeExtra("key")
```

适合存储一些局部状态数据。

### 4. View 测量参数解析（自定义 View 中使用）

```kotlin
val param = AppView.resolveMeasureParameter(this, widthMeasureSpec, heightMeasureSpec)
val maxWidth = param.maxWidth
val heightMode = param.heightModeName
```

便于在 `onMeasure()` 中获取最大宽高与模式信息。