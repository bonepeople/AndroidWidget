Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppView Usage Guide

**Source Code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppView.kt  
**This document was created with the assistance of ChatGPT.**

## Overview

`AppView` provides various extensions for Android `View` components.  
It includes single-click prevention, visibility control, per-view data storage, and layout measurement utilities.

## Key Features

### 1. Preventing Rapid Clicks

Use `.singleClick {}` to avoid multiple click triggers within a short interval.

```kotlin
button.singleClick {
    // Action to perform on safe click
}
```

You can also set a custom interval:

```kotlin
button.singleClick(1000L) {
    // Custom 1-second interval
}
```

### 2. Visibility Helpers

```kotlin
view.show()       // View.VISIBLE
view.hide()       // View.INVISIBLE
view.gone()       // View.GONE
view.switchShow(true)  // VISIBLE if true, GONE if false
view.switchVisible(false)  // INVISIBLE if false
```

### 3. Per-View Extra Data Storage

```kotlin
view.putExtra("key", 123)
val value: Int = view.getExtra("key", 0)
view.removeExtra("key")
```

### 4. MeasureSpec Analysis for Custom Views

```kotlin
val param = AppView.resolveMeasureParameter(this, widthMeasureSpec, heightMeasureSpec)
val maxWidth = param.maxWidth
val heightMode = param.heightModeName
```

This helps developers write better `onMeasure()` logic for custom views.