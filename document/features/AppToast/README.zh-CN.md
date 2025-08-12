多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppToast

## 简介

`AppToast` 可在应用任意位置显示 Toast 消息，自动切换到主线程并忽略空白消息。

## 功能

- 支持短时长和长时长
- 通过协程线程安全显示
- 自动过滤空白消息

## 使用方式

显示短时长 Toast：

```kotlin
AppToast.show("Operation completed successfully")
```

显示长时长 Toast：

```kotlin
AppToast.show("This message will stay longer", Toast.LENGTH_LONG)
```

## 注意事项

- 通过 `CoroutinesHolder.main.launch` 在主线程执行。

## 源码链接

[AppToast.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppToast.kt)