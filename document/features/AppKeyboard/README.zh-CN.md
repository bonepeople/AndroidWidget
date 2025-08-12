多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppKeyboard

## 简介

`AppKeyboard` 用于管理 Android 应用中的软键盘，提供显示/隐藏键盘的方法，以及判断触摸事件是否应触发键盘收起的能力。

## 场景

- 点击 `EditText` 外部区域时自动隐藏键盘
- 程序方式为输入框获取焦点时显示键盘
- 在应用中集中管理键盘行为

## 功能

- 根据触摸输入判断是否需要隐藏键盘
- 程序方式显示软键盘
- 隐藏软键盘

## 使用方式

在 `Activity.dispatchTouchEvent` 中处理点击外部收起键盘：

```kotlin
override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    if (AppKeyboard.needHideKeyboard(currentFocus, ev)) {
        AppKeyboard.hideKeyboard()
    }
    return super.dispatchTouchEvent(ev)
}
```

显示软键盘：

```kotlin
AppKeyboard.showKeyboard(myEditText)
```

隐藏软键盘：

```kotlin
AppKeyboard.hideKeyboard()
```

## 注意事项

- `showKeyboard()` 使用短暂延迟，确保 View 就绪后再请求焦点并显示键盘。

## 源码链接

[AppKeyboard.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppKeyboard.kt)