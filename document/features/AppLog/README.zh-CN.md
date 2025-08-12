多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppLog

## 简介

`AppLog` 是 Android 的轻量级日志工具，封装原生 `Log` 类，支持线程信息、调用栈位置及按 tag 复用日志实例。

## 场景

- 按模块或功能使用自定义 tag 记录日志
- 调试时输出线程或方法位置信息
- 集中可控的日志配置

## 功能

- 五个日志级别：`verbose`、`debug`、`info`、`warn`、`error`
- 全局及实例级日志开关
- 可选显示线程名和调用位置
- 按 tag 复用日志实例

## 使用方式

使用默认日志器：

```kotlin
AppLog.defaultLog.debug("App started.")
```

创建指定 tag 的日志器：

```kotlin
val log = AppLog.tag("MainActivity")
log.info("View created")
```

使用不同日志级别：

```kotlin
log.verbose("This is a verbose message")
log.debug("This is a debug message")
log.info("This is an info message")
log.warn("This is a warning")
log.error("This is an error")
```

启用线程和调用栈信息：

```kotlin
log.showThreadInfo = true
log.showStackInfo = true
log.stackOffset = 6 // 根据调用栈层级调整
log.debug("With stack and thread info") // [main] With stack and thread info @ MainActivity$startTest$1.invokeSuspend:38
```

控制全局日志开关：

```kotlin
AppLog.enable = false // 全局禁用日志
```

## 注意事项

- 根据方法调用深度调整 `stackOffset`。
- `AppLog.enable` 为全局开关；`AppLog.tag(...).enable` 为实例级开关。
- 生产环境避免过多 debug 日志，以免影响性能。

## 源码链接

[AppLog.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppLog.kt)