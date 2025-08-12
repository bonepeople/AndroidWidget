多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# ActivityHolder

## 简介

`ActivityHolder` 是一个用于管理应用中所有活动 `Activity` 实例的生命周期、状态及内存数据的工具对象。

## 场景

- 全局访问当前 Activity
- 跨 Activity 通信
- 生命周期感知的清理逻辑

## 功能

- 获取顶部（当前可见）Activity
- 为每个 Activity 实例存储和读取临时键值数据
- 访问按打开顺序排列的所有活动 Activity 列表

## 使用方式

### 获取顶部 Activity

```kotlin
val currentActivity = ActivityHolder.getTopActivity()
```

### 临时 Activity 数据

存储数据：

```kotlin
ActivityHolder.putData(activity, "key", value)
```

读取数据：

```kotlin
val value = ActivityHolder.getData(activity, "key")
```

移除数据：

```kotlin
ActivityHolder.removeData(activity, "key")
```

### 所有活动 Activity

```kotlin
val allActivities = ActivityHolder.activityList
```

列表按 Activity 打开顺序排列。

## 源码链接

[ActivityHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ActivityHolder.kt)