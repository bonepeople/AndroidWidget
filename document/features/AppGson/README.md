Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# AppGson

## Introduction

`AppGson` is a JSON serialization and deserialization utility based on Gson. It improves Gson's default behavior by automatically removing `null` values during deserialization, enhancing null safety in Kotlin environments.

## Use Cases

- Ignore `null` fields during JSON parsing
- Avoid `NullPointerException` in Kotlin
- Centralize JSON behavior across the app

## Features

- Default `Gson` instance that removes `null` values from JSON
- Object-to-JSON and JSON-to-object conversion with generic type support
- Custom `Gson` instance support
- `addNotNullAdapter` for enhancing any `Gson` instance with null filtering

## Usage

Convert an object to JSON:

```kotlin
val json = AppGson.toJson(User(name = "Tom", age = 25))
```

Convert JSON to an object:

```kotlin
val json = """{"name":"Tom","age":25}"""
val user: User = AppGson.toObject(json)
```

Use a custom `Gson` instance:

```kotlin
val customGson = GsonBuilder().create()
val json = AppGson.toJson(data, customGson)
```

Use `addNotNullAdapter` to remove `null` fields:

```kotlin
val gson = AppGson.addNotNullAdapter(GsonBuilder().create())
val user = gson.fromJson<User>("""{"name":"Tom","age":null}""", User::class.java)
// The null value for age will be removed automatically
```

When deserializing JSON, fields with `null` values are normally assigned directly to object fields, which can cause null references in Kotlin. `addNotNullAdapter` removes all `null` values from JSON objects and arrays — including nested structures — so you no longer need to manually check for nulls on every access.

## Notes

- Empty JSON strings throw an `IllegalStateException`.
- `toObject` supports generic type inference.
- `addNotNullAdapter` returns a modified `Gson` instance — reuse it when possible.

## Source Code

[AppGson.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppGson.kt)