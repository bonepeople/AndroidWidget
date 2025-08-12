多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# BreatheInterpolator

## 简介

`BreatheInterpolator` 是自定义动画插值器，模拟自然呼吸节奏。接受 `0` 到 `1` 之间的输入值，返回同范围内的修改值，产生平滑、有机的动画效果。

## 场景

- 呼吸灯按钮和加载指示器
- 需要自然缓动曲线的 UI 元素

## 功能

- 呼吸式缓动曲线
- 两种插值模式：
  - `FROM_BOTTOM`：从最小值上升（默认）
  - `FROM_TOP`：从最大值下降

## 使用方式

应用到动画（如 `ObjectAnimator` 或 `ValueAnimator`）：

```kotlin
val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
    duration = 2000
    interpolator = BreatheInterpolator() // 默认模式: FROM_BOTTOM
}
animator.start()
```

指定从顶部开始的模式：

```kotlin
val animator = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.2f).apply {
    duration = 3000
    interpolator = BreatheInterpolator(BreatheInterpolator.FROM_TOP)
}
animator.start()
```

模式常量：

```kotlin
BreatheInterpolator.FROM_BOTTOM // value = 1
BreatheInterpolator.FROM_TOP    // value = 2
```

## 源码链接

[BreatheInterpolator.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/animation/BreatheInterpolator.kt)