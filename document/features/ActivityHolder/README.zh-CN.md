多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# ActivityHolder

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ActivityHolder.kt

`ActivityHolder` 是一个用于管理当前所有活动中的 `Activity` 实例及其临时状态数据的工具类。

它能够跟踪所有已打开的 Activity，并为每个 Activity 提供独立的数据存储空间。

> 📄 本文档由 ChatGPT 协助完成。

## 使用方法

### 获取当前顶部 Activity

可获取当前显示在最前端的 Activity：

```kotlin
val currentActivity = ActivityHolder.getTopActivity()
```

### 为某个 Activity 存储临时数据

你可以为指定 Activity 存储键值对数据。

#### 存储数据：

```kotlin
ActivityHolder.putData(activity, "key", value)
```

#### 获取数据：

```kotlin
val value = ActivityHolder.getData(activity, "key")
```

#### 移除数据：

```kotlin
ActivityHolder.removeData(activity, "key")
```

### 获取所有活动中的 Activity 实例列表

```kotlin
val allActivities = ActivityHolder.activityList
```

该列表会按照 Activity 启动顺序排列。

## 注意事项

- 适用于全局访问、跨 Activity 通信或统一资源清理等场景。