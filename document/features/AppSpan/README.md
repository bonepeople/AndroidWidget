Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppSpan

## Introduction

`AppSpan` simplifies the creation of rich-text content in Android using spans. It extends `SpannableStringBuilder` and provides chainable methods for styling text with color, size, background, and more.

## Use Cases

- Highlighted labels and custom titles
- Composite styled paragraphs in `TextView`, Toast, or Snackbar

## Features

- Chainable API for appending styled text
- Built-in helpers for foreground color, text size, relative scale, and background color
- Compatible anywhere `CharSequence` is accepted

## Usage

Append styled text with one or more span effects:

```kotlin
val span = AppSpan()
    .text("Hello ", ForegroundColorSpan(Color.RED))
    .text("World", BackgroundColorSpan(Color.YELLOW))
```

Built-in helper methods:

```kotlin
val colorSpan = AppSpan.textColor("Hello", Color.BLUE)
val sizeSpan = AppSpan.textSize("Large Text", 20f)
val scaleSpan = AppSpan.textScale("Scaled Text", 1.5f)
val bgSpan = AppSpan.backgroundColor("Highlighted", Color.LTGRAY)
```

Chain multiple style methods:

```kotlin
val span = AppSpan()
    .textColor("Red", Color.RED)
    .textSize(" Big", 18f)
    .backgroundColor(" Highlight", Color.YELLOW)
```

## Notes

- Effects are applied using Android's standard span classes.

## Source Code

[AppSpan.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSpan.kt)