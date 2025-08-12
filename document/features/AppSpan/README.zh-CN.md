多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppSpan

## 简介

`AppSpan` 简化 Android 富文本内容的创建，继承 `SpannableStringBuilder`，提供链式 API 用于设置文字颜色、大小、背景等样式。

## 场景

- 高亮标签和自定义标题
- 在 `TextView`、Toast、Snackbar 等处的复合样式段落

## 功能

- 链式 API 追加带样式文本
- 内置前景色、字号、相对缩放、背景色等辅助方法
- 兼容所有接受 `CharSequence` 的场景

## 使用方式

追加带一种或多种 span 效果的文本：

```kotlin
val span = AppSpan()
    .text("Hello ", ForegroundColorSpan(Color.RED))
    .text("World", BackgroundColorSpan(Color.YELLOW))
```

内置辅助方法：

```kotlin
val colorSpan = AppSpan.textColor("Hello", Color.BLUE)
val sizeSpan = AppSpan.textSize("Large Text", 20f)
val scaleSpan = AppSpan.textScale("Scaled Text", 1.5f)
val bgSpan = AppSpan.backgroundColor("Highlighted", Color.LTGRAY)
```

链式组合多种样式：

```kotlin
val span = AppSpan()
    .textColor("Red", Color.RED)
    .textSize(" Big", 18f)
    .backgroundColor(" Highlight", Color.YELLOW)
```

## 注意事项

- 样式基于 Android 标准 span 类实现。

## 源码链接

[AppSpan.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSpan.kt)