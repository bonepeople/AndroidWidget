多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppSpan 使用指南

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSpan.kt  
**本文档由 ChatGPT 协助完成。**

## 简介

`AppSpan` 是一个用于构建富文本的工具类，继承自 `SpannableStringBuilder`，提供便捷的方法来设置文字颜色、背景色、文字大小、缩放比例等样式。

> 适用于制作动态样式文本，如高亮标签、自定义标题、组合样式段落等。

## 使用方法

### 1. 添加带样式的文本段

可使用 `text` 方法添加带多个效果的文本：

```kotlin
val span = AppSpan()
    .text("你好", ForegroundColorSpan(Color.RED))
    .text("世界", BackgroundColorSpan(Color.YELLOW))
```

### 2. 使用内置快捷方法

#### 设置文字颜色：

```kotlin
val span = AppSpan.textColor("蓝色文字", Color.BLUE)
```

#### 设置绝对文字大小（单位 SP）：

```kotlin
val span = AppSpan.textSize("大字体", 20f)
```

#### 设置文字缩放比例：

```kotlin
val span = AppSpan.textScale("放大文字", 1.5f)
```

#### 设置文字背景色：

```kotlin
val span = AppSpan.backgroundColor("背景文字", Color.LTGRAY)
```

### 3. 链式调用示例

```kotlin
val span = AppSpan()
    .textColor("红色", Color.RED)
    .textSize(" 大号", 18f)
    .backgroundColor(" 高亮", Color.YELLOW)
```

## 注意事项

- `AppSpan` 可用于任何支持 `CharSequence` 的控件，例如 `TextView`、`Toast` 或 `Snackbar`。
- 所有效果均基于 Android 原生的 Span 类型实现。