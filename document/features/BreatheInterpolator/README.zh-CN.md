多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# BreatheInterpolator 使用文档

**源代码链接：** https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/animation/BreatheInterpolator.kt  
**本文档由 ChatGPT 协助完成。*

## 简介

`BreatheInterpolator` 是一个自定义动画插值器，用于模拟自然的呼吸节奏，适合在 Android 应用中创建柔和、有机的动画效果。

## 功能特点

- 接受 `0` 到 `1` 范围内的输入值，返回同范围内的平滑值。
- 模拟呼吸节奏的缓动曲线。
- 支持两种插值模式：
    - `FROM_BOTTOM`：动画从底部开始上升（默认）。
    - `FROM_TOP`：动画从顶部开始下降。

## 使用方式

### 1. 添加插值器

将 `BreatheInterpolator` 应用于 `ObjectAnimator` 或 `ValueAnimator`：

```kotlin
val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
    duration = 2000
    interpolator = BreatheInterpolator() // 默认模式 FROM_BOTTOM
}
animator.start()
```

### 2. 指定插值模式（可选）

如果你希望动画从顶部开始，可以指定 `FROM_TOP` 模式：

```kotlin
val animator = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.2f).apply {
    duration = 3000
    interpolator = BreatheInterpolator(BreatheInterpolator.FROM_TOP)
}
animator.start()
```

## 常量说明

```kotlin
BreatheInterpolator.FROM_BOTTOM // 值为 1，表示从底部开始
BreatheInterpolator.FROM_TOP    // 值为 2，表示从顶部开始
```

可使用以上常量来控制动画的起始方向。

## 总结

该插值器为动画添加了自然、节奏感强的“呼吸”效果，适用于按钮呼吸动画、加载提示动画等需要柔和缓动效果的 UI 元素。