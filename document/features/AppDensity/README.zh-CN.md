多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppDensity

## 简介

`AppDensity` 提供 Android 中常用的像素密度转换方法，支持 px、dp、sp 之间的相互转换。

## 功能

- dp / sp 转 px
- px 转 dp / sp
- 支持自定义或自动获取 `DisplayMetrics`

## 使用方式

```kotlin
val px = AppDensity.getPx(16f) // 16dp -> px
val dp = AppDensity.getDp(32)  // px -> dp
val sp = AppDensity.getSp(24)  // px -> sp
```

指定自定义 `DisplayMetrics`：

```kotlin
val customMetrics = Resources.getSystem().displayMetrics
val px = AppDensity.getPx(16f, TypedValue.COMPLEX_UNIT_SP, customMetrics)
```

## 源码链接

[AppDensity.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppDensity.kt)