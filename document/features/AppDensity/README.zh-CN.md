多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppDensity 工具类

本工具类提供了 Android 上常用的像素密度单位转换方法，包括 px 、dp 、sp 之间的互相转换。

## 功能摘要

* 支持将 dp / sp 转换为 px
* 支持将 px 转换为 dp / sp
* 支持指定或自动选择屏幕属性 DisplayMetrics

## 使用方法

```kotlin
val px = AppDensity.getPx(16f) // 16dp -> px
val dp = AppDensity.getDp(32)  // px -> dp
val sp = AppDensity.getSp(24)  // px -> sp
```

你也可以指定自己的 DisplayMetrics 对象：

```kotlin
val customMetrics = Resources.getSystem().displayMetrics
val px = AppDensity.getPx(16f, TypedValue.COMPLEX_UNIT_SP, customMetrics)
```

## 源代码

[点击查看源代码](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppDensity.kt)

---

本文档由 ChatGPT 协助生成