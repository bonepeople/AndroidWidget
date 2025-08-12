多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppView

## 简介

`AppView` 为 Android `View` 组件提供扩展能力，包括防重复点击、可见性控制、View 级数据存储及布局测量工具。

## 功能

- 可配置间隔的防快速重复点击
- 可见性辅助方法（`show`、`hide`、`gone`、`switchShow`、`switchVisible`）
- View 级额外数据存储（键值对）
- 自定义 View 布局的 `MeasureSpec` 分析

## 使用方式

防快速重复点击：

```kotlin
button.singleClick {
    // 安全点击时执行的操作
}

button.singleClick(1000L) {
    // 自定义 1 秒间隔
}
```

可见性辅助：

```kotlin
view.show()                  // View.VISIBLE
view.hide()                  // View.INVISIBLE
view.gone()                  // View.GONE
view.switchShow(true)        // true 时为 VISIBLE，否则 GONE
view.switchVisible(false)    // false 时为 INVISIBLE
```

View 级额外数据存储：

```kotlin
view.putExtra("key", 123)
val value: Int = view.getExtra("key", 0)
view.removeExtra("key")
```

自定义 View 的 `MeasureSpec` 分析：

```kotlin
val param = AppView.resolveMeasureParameter(this, widthMeasureSpec, heightMeasureSpec)
val maxWidth = param.maxWidth
val heightMode = param.heightModeName
```

有助于编写更清晰的自定义 View `onMeasure()` 逻辑。

## 源码链接

[AppView.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppView.kt)