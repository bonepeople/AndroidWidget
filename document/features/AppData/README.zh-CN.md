多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppData 使用指南

> 本文档由 ChatGPT 协助完成  
> 源代码链接：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppData.kt

## 简介

`AppData` 是一个基于 Jetpack DataStore 的数据存储工具类，用于在 Android 应用中以键值对方式高效、可靠地存储持久化配置数据。该工具类支持异步（suspend）与同步（阻塞）两种访问方式，并支持多命名空间（storeName）实例化。

## 功能概览

- 支持多种基础类型（String、Int、Long、Float、Double、Boolean）的读写；
- 提供 suspend 异步方法和阻塞同步方法；
- 支持通过 Flow 监听数据变化；
- 允许通过 `create()` 创建多个命名空间的数据仓库；
- 自动创建配置文件用于记录版本号与实例表。

## 如何使用

### 1. 获取默认数据仓库

```kotlin
val data = AppData.default
```

### 2. 创建自定义命名空间的数据仓库

```kotlin
val config = AppData.create("settings")
```

### 3. 存储数据（异步）

```kotlin
runBlocking {
    data.putString("username", "jack")
    data.putInt("launchCount", 5)
}
```

### 4. 读取数据（异步）

```kotlin
val username = data.getString("username", "guest")
```

### 5. 存储/读取数据（同步）

```kotlin
data.putBooleanSync("night_mode", true)
val enabled = data.getBooleanSync("night_mode", false)
```

### 6. 响应式读取（Flow）

```kotlin
data.getIntFlow("launchCount").collect {
    Log.d("Launch Count", "$it")
}
```

## 推荐使用场景

- 替代 SharedPreferences 实现更安全、支持协程的数据存储；
- 管理多模块或多用户的配置数据；
- 需要监听数据实时变化时使用 Flow；
- 需要数据存储同步版本控制与表管理机制。

## 注意事项

- `storeName` 不能包含非法字符（如 `<>:"/\|?*`）或以 `.` 或空格结尾；
- 同步方法内部使用 `runBlocking`，不建议在主线程调用；