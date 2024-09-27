多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppKeyboard 使用指南

> 本文档由 ChatGPT 协助完成  
> 源代码链接：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppKeyboard.kt

## 简介

`AppKeyboard` 是一个用于控制软件输入法（软键盘）的工具类，提供在 Android 应用中显示与隐藏软键盘的便捷方法，并支持判断点击区域是否需要隐藏键盘。

## 功能概览

- 判断是否应隐藏软键盘；
- 显示软键盘；
- 隐藏软键盘。

## 如何使用

### 1. 判断是否需要隐藏软键盘

当用户点击 EditText 以外区域时，可以调用此方法判断是否应关闭键盘：

```kotlin
override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
    if (AppKeyboard.needHideKeyboard(currentFocus, ev)) {
        AppKeyboard.hideKeyboard()
    }
    return super.dispatchTouchEvent(ev)
}
```

### 2. 主动显示软键盘

```kotlin
AppKeyboard.showKeyboard(myEditText)
```

### 3. 主动隐藏软键盘

```kotlin
AppKeyboard.hideKeyboard()
```

## 推荐使用场景

- 实现点击空白区域自动隐藏软键盘；
- 在特定输入场景中主动弹出软键盘；
- 统一管理应用中的键盘显示与隐藏逻辑。

## 注意事项

- `showKeyboard()` 方法通过延迟处理确保在视图渲染后打开键盘。