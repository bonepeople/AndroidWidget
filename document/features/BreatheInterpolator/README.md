Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# BreatheInterpolator

## Introduction

`BreatheInterpolator` is a custom animation interpolator that simulates a natural breathing rhythm. It accepts an input value between `0` and `1` and returns a modified value in the same range, producing smooth and organic animation effects.

## Use Cases

- Breathing buttons and loading indicators
- Any UI element that needs a natural easing pattern

## Features

- Breathing-like easing curve
- Two interpolation modes:
  - `FROM_BOTTOM`: starts from minimum and rises (default)
  - `FROM_TOP`: starts from maximum and falls

## Usage

Apply to an animation (e.g. `ObjectAnimator` or `ValueAnimator`):

```kotlin
val animator = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f).apply {
    duration = 2000
    interpolator = BreatheInterpolator() // Default mode: FROM_BOTTOM
}
animator.start()
```

Specify the mode to start from the top:

```kotlin
val animator = ObjectAnimator.ofFloat(view, "scaleX", 0.8f, 1.2f).apply {
    duration = 3000
    interpolator = BreatheInterpolator(BreatheInterpolator.FROM_TOP)
}
animator.start()
```

Mode constants:

```kotlin
BreatheInterpolator.FROM_BOTTOM // value = 1
BreatheInterpolator.FROM_TOP    // value = 2
```

## Source Code

[BreatheInterpolator.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/animation/BreatheInterpolator.kt)