Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppSpan Usage Guide

**Source Code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppSpan.kt  
**This document was created with the assistance of ChatGPT.**

## Overview

`AppSpan` is a utility class designed to simplify the creation of rich-text content in Android using spans. It extends `SpannableStringBuilder` and provides convenient methods for styling text with color, size, background, and more.

> Suitable for creating dynamic, styled text such as highlighted labels, custom titles, and composite paragraphs.

## How to Use

### 1. Append Styled Text

Add text with one or more span effects (color, size, background, etc.):

```kotlin
val span = AppSpan()
    .text("Hello ", ForegroundColorSpan(Color.RED))
    .text("World", BackgroundColorSpan(Color.YELLOW))
```

### 2. Use Built-in Helper Methods

#### Text with foreground color:

```kotlin
val span = AppSpan.textColor("Hello", Color.BLUE)
```

#### Text with absolute size (in SP):

```kotlin
val span = AppSpan.textSize("Large Text", 20f)
```

#### Text with relative scale:

```kotlin
val span = AppSpan.textScale("Scaled Text", 1.5f)
```

#### Text with background color:

```kotlin
val span = AppSpan.backgroundColor("Highlighted", Color.LTGRAY)
```

### 3. Chain Methods

You can chain multiple style methods:

```kotlin
val span = AppSpan()
    .textColor("Red", Color.RED)
    .textSize(" Big", 18f)
    .backgroundColor(" Highlight", Color.YELLOW)
```

## Notes

- `AppSpan` can be used anywhere `CharSequence` is accepted (e.g., TextView, Toast, Snackbar).
- Effects are applied using Android’s standard span classes.