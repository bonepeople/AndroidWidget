Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppKeyboard

## Introduction

`AppKeyboard` manages the software keyboard in Android apps. It provides methods to show or hide the keyboard and detect whether a touch event should trigger keyboard dismissal.

## Use Cases

- Automatically hide the keyboard when tapping outside an `EditText`
- Show the keyboard when an input field gains focus programmatically
- Centralize keyboard behavior across the app

## Features

- Detect whether the keyboard should be hidden based on touch input
- Show the soft keyboard programmatically
- Hide the soft keyboard

## Usage

Hide the keyboard when the user taps outside an input field — use in `Activity.dispatchTouchEvent`:

```kotlin
override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    if (AppKeyboard.needHideKeyboard(currentFocus, ev)) {
        AppKeyboard.hideKeyboard()
    }
    return super.dispatchTouchEvent(ev)
}
```

Show the soft keyboard:

```kotlin
AppKeyboard.showKeyboard(myEditText)
```

Hide the soft keyboard:

```kotlin
AppKeyboard.hideKeyboard()
```

## Notes

- `showKeyboard()` uses a short delay to ensure the view is ready before requesting focus and showing the keyboard.

## Source Code

[AppKeyboard.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppKeyboard.kt)