Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppView

## Introduction

`AppView` provides extensions for Android `View` components, including single-click prevention, visibility control, per-view data storage, and layout measurement utilities.

## Features

- Prevent rapid repeated clicks with configurable interval
- Visibility helpers (`show`, `hide`, `gone`, `switchShow`, `switchVisible`)
- Per-view extra data storage (key-value)
- `MeasureSpec` analysis for custom view layout

## Usage

Prevent rapid clicks:

```kotlin
button.singleClick {
    // Action to perform on safe click
}

button.singleClick(1000L) {
    // Custom 1-second interval
}
```

Visibility helpers:

```kotlin
view.show()                  // View.VISIBLE
view.hide()                  // View.INVISIBLE
view.gone()                  // View.GONE
view.switchShow(true)        // VISIBLE if true, GONE if false
view.switchVisible(false)    // INVISIBLE if false
```

Per-view extra data storage:

```kotlin
view.putExtra("key", 123)
val value: Int = view.getExtra("key", 0)
view.removeExtra("key")
```

`MeasureSpec` analysis for custom views:

```kotlin
val param = AppView.resolveMeasureParameter(this, widthMeasureSpec, heightMeasureSpec)
val maxWidth = param.maxWidth
val heightMode = param.heightModeName
```

This helps write better `onMeasure()` logic for custom views.

## Source Code

[AppView.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppView.kt)