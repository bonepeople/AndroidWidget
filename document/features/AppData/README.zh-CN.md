多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppData

## 简介

`AppData` 是基于 Jetpack DataStore 的键值存储工具，用于在 Android 应用中高效持久化配置数据。支持异步（suspend）和阻塞（同步）访问，以及按名称创建多个隔离实例。

## 场景

- 用协程安全的现代方案替代 `SharedPreferences`
- 管理多用户或多模块的配置
- 通过响应式流监听配置变更
- 在多个偏好存储中跟踪数据版本

## 功能

- 读写基本类型（String、Int、Long、Float、Double、Boolean）
- 提供 suspend 函数和同步 API
- 通过 `Flow` 响应式访问
- 支持多个命名数据存储实例
- 自动管理版本和注册信息

## 使用方式

使用默认数据存储：

```kotlin
val data = AppData.default
```

创建自定义命名存储：

```kotlin
val config = AppData.create("settings")
```

写入数据（suspend）：

```kotlin
runBlocking {
    data.putString("username", "jack")
    data.putInt("launchCount", 5)
}
```

读取数据（suspend）：

```kotlin
val username = data.getString("username", "guest")
```

同步读写：

```kotlin
data.putBooleanSync("night_mode", true)
val enabled = data.getBooleanSync("night_mode", false)
```

通过 `Flow` 监听变更：

```kotlin
data.getIntFlow("launchCount").collect {
    Log.d("Launch Count", "$it")
}
```

## 注意事项

- `storeName` 不能包含非法字符（`<>:"/\|?*`），也不能以句号或空格结尾。
- 同步 API 使用 `runBlocking`，避免在主线程调用。

## 源码链接

[AppData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppData.kt)