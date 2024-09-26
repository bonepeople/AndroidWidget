多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# AppGson 使用指南

> 本文档由 ChatGPT 协助完成  
> 源代码链接：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppGson.kt

## 简介

`AppGson` 是一个基于 Gson 的 JSON 工具类，提供便捷的对象与 JSON 字符串的相互转换能力。相比默认的 Gson 实例，它增强了对空值的处理能力，在反序列化过程中自动移除 null 值，提升了 Kotlin 中的空安全性。

## 功能概览

- 提供默认配置的 `Gson` 实例，自动移除 JSON 中的 null 值；
- 提供对象转 JSON 的方法；
- 提供 JSON 字符串转对象的泛型方法；
- 支持自定义 `Gson` 实例；
- 提供 `addNotNullAdapter` 方法用于增强现有 `Gson` 实例的 null 值过滤能力。

## 如何使用

### 1. 将对象转换为 JSON 字符串

```kotlin
val json = AppGson.toJson(User(name = "Tom", age = 25))
```

### 2. 将 JSON 字符串转换为对象

```kotlin
val json = """{"name":"Tom","age":25}"""
val user: User = AppGson.toObject(json)
```

### 3. 使用自定义 Gson 实例

```kotlin
val customGson = GsonBuilder().create()
val json = AppGson.toJson(data, customGson)
```

### 4. 使用 `addNotNullAdapter` 过滤 null 值

```kotlin
val gson = AppGson.addNotNullAdapter(GsonBuilder().create())
val user = gson.fromJson<User>("""{"name":"Tom","age":null}""", User::class.java)
// age 字段的 null 值会被自动去除
```

#### 使用场景说明

在反序列化 JSON 字符串时，如果字段值为 `null`，会直接赋值给对象字段。这会导致字段值为 `null`，影响正常使用，且在 Kotlin 中会引发空指针异常（NPE）。

通过使用 `addNotNullAdapter` 添加一个自动去除 `null` 的适配器，可以在解析过程中移除所有嵌套结构中的 `null` 值，从而避免开发者在每次使用字段时都需要手动判空。

## 推荐使用场景

- 希望在反序列化时自动忽略 JSON 中的 null 字段；
- 避免 Kotlin 中出现 `NullPointerException`；
- 希望统一管理 JSON 的序列化与解析行为。

## 注意事项

- 空 JSON 字符串将抛出 `IllegalStateException`；
- 对于带泛型的类，`toObject` 方法支持自动推断；
- `addNotNullAdapter` 返回的是增强后的 `Gson` 实例，请合理复用。