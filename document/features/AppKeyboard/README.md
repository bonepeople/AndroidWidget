Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppKeyboard Usage Guide

> This document was generated with the help of ChatGPT  
> Source code link: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppKeyboard.kt

## Introduction

`AppKeyboard` is a utility class for managing the software keyboard in Android apps. It provides simple methods to show or hide the keyboard and detect whether a touch event should trigger keyboard dismissal.

## Features

- Detect whether the keyboard should be hidden based on touch input;
- Show the soft keyboard programmatically;
- Hide the soft keyboard.

## How to Use

### 1. Determine if the keyboard should be hidden

Use this in `Activity.dispatchTouchEvent` to hide the keyboard when the user taps outside of an input field:

```kotlin
override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    if (AppKeyboard.needHideKeyboard(currentFocus, ev)) {
        AppKeyboard.hideKeyboard()
    }
    return super.dispatchTouchEvent(ev)
}
```

### 2. Show the soft keyboard

```kotlin
AppKeyboard.showKeyboard(myEditText)
```

### 3. Hide the soft keyboard

```kotlin
AppKeyboard.hideKeyboard()
```

## Recommended Use Cases

- Automatically hide the keyboard when tapping outside EditText;
- Show the keyboard when an input field gains focus programmatically;
- Centralize keyboard behavior management in your app.

## Notes

- `showKeyboard()` uses a short delay to ensure the view is ready before requesting focus and showing the keyboard.