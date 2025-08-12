多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppGson

## 简介

`AppGson` 是基于 Gson 的 JSON 序列化与反序列化工具。它在反序列化时自动移除 `null` 值，提升 Kotlin 环境下的空安全能力。

## 场景

- 解析 JSON 时忽略 `null` 字段
- 避免 Kotlin 中的 `NullPointerException`
- 在应用中集中管理 JSON 行为

## 功能

- 默认 `Gson` 实例，自动移除 JSON 中的 `null` 值
- 对象与 JSON 互转，支持泛型类型推断
- 支持自定义 `Gson` 实例
- `addNotNullAdapter` 可为任意 `Gson` 实例增强 null 过滤能力

## 使用方式

对象转 JSON：

```kotlin
val json = AppGson.toJson(User(name = "Tom", age = 25))
```

JSON 转对象：

```kotlin
val json = """{"name":"Tom","age":25}"""
val user: User = AppGson.toObject(json)
```

使用自定义 `Gson` 实例：

```kotlin
val customGson = GsonBuilder().create()
val json = AppGson.toJson(data, customGson)
```

使用 `addNotNullAdapter` 移除 `null` 字段：

```kotlin
val gson = AppGson.addNotNullAdapter(GsonBuilder().create())
val user = gson.fromJson<User>("""{"name":"Tom","age":null}""", User::class.java)
// age 的 null 值会被自动移除
```

反序列化时，`null` 字段通常会直接赋给对象属性，在 Kotlin 中容易引发空引用。`addNotNullAdapter` 会从 JSON 对象和数组（包括嵌套结构）中移除所有 `null` 值，无需每次访问时手动判空。

## 注意事项

- 空 JSON 字符串会抛出 `IllegalStateException`。
- `toObject` 支持泛型类型推断。
- `addNotNullAdapter` 返回修改后的 `Gson` 实例，建议复用。

## 源码链接

[AppGson.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppGson.kt)