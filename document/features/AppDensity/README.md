Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppDensity Utility

This utility provides commonly used pixel density conversion methods in Android, including conversions between px, dp, and sp units.

## Features

* Convert dp / sp to px
* Convert px to dp / sp
* Supports specifying or automatically determining DisplayMetrics

## Usage

```kotlin
val px = AppDensity.getPx(16f) // 16dp -> px
val dp = AppDensity.getDp(32)  // px -> dp
val sp = AppDensity.getSp(24)  // px -> sp
```

You can also specify your own `DisplayMetrics`:

```kotlin
val customMetrics = Resources.getSystem().displayMetrics
val px = AppDensity.getPx(16f, TypedValue.COMPLEX_UNIT_SP, customMetrics)
```

## Source Code

[View source code](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppDensity.kt)

---

This documentation was generated with the assistance of ChatGPT.