Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# BreatheInterpolator Documentation

**Source Code:** https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/animation/BreatheInterpolator.kt  
**This documentation was created with assistance from ChatGPT.*

## Overview

`BreatheInterpolator` is a custom animation interpolator designed to simulate a natural breathing rhythm. It can be used to create smooth and organic animation effects in Android applications.

## Features

- Accepts an input value between `0` and `1`, and returns a modified value in the same range.
- Simulates a breathing-like easing curve.
- Offers two modes of interpolation:
    - `FROM_BOTTOM`: Animation starts from a minimum value and rises (default).
    - `FROM_TOP`: Animation starts from a maximum value and falls.

## How to Use

### 1. Add the Interpolator

To use the `BreatheInterpolator`, instantiate it and apply it to an Android animation (e.g., `ObjectAnimator` or `ValueAnimator`):

```kotlin
val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
    duration = 2000
    interpolator = BreatheInterpolator() // Default mode: FROM_BOTTOM
}
animator.start()
```

### 2. Specify Mode (Optional)

You can specify the mode to start the animation from the top instead of the bottom:

```kotlin
val animator = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.2f).apply {
    duration = 3000
    interpolator = BreatheInterpolator(BreatheInterpolator.FROM_TOP)
}
animator.start()
```

## Constants

```kotlin
BreatheInterpolator.FROM_BOTTOM // value = 1
BreatheInterpolator.FROM_TOP    // value = 2
```

Use these constants when creating an interpolator to control the animation direction.

## Summary

This interpolator adds a subtle, rhythmic breathing effect to animations, ideal for UI components like breathing buttons, loading indicators, or any element requiring a natural easing pattern.