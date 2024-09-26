Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppGson Usage Guide

> This document was generated with the help of ChatGPT  
> Source code link: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppGson.kt

## Introduction

`AppGson` is a utility class for JSON serialization and deserialization based on Gson. It improves Gson's default behavior by automatically removing `null` values during deserialization, enhancing null safety, especially in Kotlin environments.

## Features

- Provides a default `Gson` instance that removes `null` values from JSON;
- Converts objects to JSON strings;
- Converts JSON strings to typed objects using generics;
- Allows the use of custom `Gson` instances;
- Includes `addNotNullAdapter` for enhancing any `Gson` instance with null filtering.

## How to Use

### 1. Convert an object to a JSON string

```kotlin
val json = AppGson.toJson(User(name = "Tom", age = 25))
```

### 2. Convert a JSON string to an object

```kotlin
val json = """{"name":"Tom","age":25}"""
val user: User = AppGson.toObject(json)
```

### 3. Use a custom Gson instance

```kotlin
val customGson = GsonBuilder().create()
val json = AppGson.toJson(data, customGson)
```

### 4. Use `addNotNullAdapter` to remove `null` fields

```kotlin
val gson = AppGson.addNotNullAdapter(GsonBuilder().create())
val user = gson.fromJson<User>("""{"name":"Tom","age":null}""", User::class.java)
// The null value for age will be removed automatically
```

#### Why use `addNotNullAdapter`

When deserializing JSON, fields with `null` values are normally assigned directly to object fields, which can cause null references and crashes in Kotlin.

By using `addNotNullAdapter`, a special adapter is added that removes all `null` values from JSON objects and arrays—even in nested structures—so you no longer need to manually check for nulls every time you use a value.

## Recommended Use Cases

- You want to ignore `null` fields during JSON parsing;
- You need to avoid `NullPointerException` in Kotlin;
- You prefer centralized management of JSON behavior.

## Notes

- Empty JSON strings will throw an `IllegalStateException`;
- `toObject` supports generic type inference;
- `addNotNullAdapter` returns a modified `Gson` instance—reuse when possible.