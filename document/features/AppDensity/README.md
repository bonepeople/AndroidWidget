Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppDensity

## Introduction

`AppDensity` provides pixel density conversion methods for Android, including conversions between px, dp, and sp units.

## Features

- Convert dp / sp to px
- Convert px to dp / sp
- Support custom or automatically determined `DisplayMetrics`

## Usage

```kotlin
val px = AppDensity.getPx(16f) // 16dp -> px
val dp = AppDensity.getDp(32)  // px -> dp
val sp = AppDensity.getSp(24)  // px -> sp
```

Specify a custom `DisplayMetrics`:

```kotlin
val customMetrics = Resources.getSystem().displayMetrics
val px = AppDensity.getPx(16f, TypedValue.COMPLEX_UNIT_SP, customMetrics)
```

## Source Code

[AppDensity.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppDensity.kt)