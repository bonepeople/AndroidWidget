多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppLog 使用指南

> 本文档由 ChatGPT 协助完成  
> 源代码链接：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppLog.kt

## 简介

`AppLog` 是一个简洁而灵活的日志工具类，基于 Android 的 `Log` 实现封装。支持多 tag 实例复用、线程信息与调用堆栈位置的可选输出，适用于调试和开发阶段的日志记录需求。

## 功能概览

- 提供 `verbose`, `debug`, `info`, `warn`, `error` 五种日志级别；
- 支持开启或关闭日志输出；
- 可选输出当前线程信息；
- 可选输出调用方法的类名与行号；
- 支持多个 tag 实例复用。

## 如何使用

### 1. 获取默认日志实例

```kotlin
AppLog.defaultLog.debug("App started.")
```

### 2. 创建带 tag 的日志实例

```kotlin
val log = AppLog.tag("MainActivity")
log.info("View created")
```

### 3. 输出不同级别日志

```kotlin
log.verbose("This is a verbose message")
log.debug("This is a debug message")
log.info("This is an info message")
log.warn("This is a warning")
log.error("This is an error")
```

### 4. 启用线程信息和堆栈信息

```kotlin
log.showThreadInfo = true
log.showStackInfo = true
log.stackOffset = 6 // 根据调用栈层级调整
log.debug("With stack and thread info") // [main] With stack and thread info @ MainActivity$startTest$1.invokeSuspend:38
```

### 5. 全局控制日志输出开关

```kotlin
AppLog.enable = false // 禁用所有日志输出
```

## 推荐使用场景

- 需要区分不同模块日志的开发项目；
- 希望日志中包含线程名、调用方法等调试信息；
- 需要一个轻量、可集中管理日志输出行为的工具。

## 注意事项

- `showStackInfo` 需要适当设置 `stackOffset`，以匹配实际调用层级；
- `AppLog.enable` 控制全局开关，`AppLog.tag(...).enable` 控制局部；
- 建议避免在正式发布版本中开启过多调试信息，以免影响性能。