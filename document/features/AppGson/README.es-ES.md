Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppGson

## Introducción

`AppGson` es una utilidad de serialización y deserialización JSON basada en Gson. Mejora el comportamiento predeterminado de Gson eliminando automáticamente valores `null` durante la deserialización, mejorando la seguridad nula en entornos Kotlin.

## Casos de uso

- Ignorar campos `null` durante el análisis JSON
- Evitar `NullPointerException` en Kotlin
- Centralizar el comportamiento JSON en la app

## Características

- Instancia `Gson` predeterminada que elimina valores `null` del JSON
- Conversión objeto-JSON y JSON-objeto con soporte de tipos genéricos
- Soporte para instancias `Gson` personalizadas
- `addNotNullAdapter` para mejorar cualquier instancia `Gson` con filtrado de nulos

## Uso

Convertir un objeto a JSON:

```kotlin
val json = AppGson.toJson(User(name = "Tom", age = 25))
```

Convertir JSON a objeto:

```kotlin
val json = """{"name":"Tom","age":25}"""
val user: User = AppGson.toObject(json)
```

Usar una instancia `Gson` personalizada:

```kotlin
val customGson = GsonBuilder().create()
val json = AppGson.toJson(data, customGson)
```

Usar `addNotNullAdapter` para eliminar campos `null`:

```kotlin
val gson = AppGson.addNotNullAdapter(GsonBuilder().create())
val user = gson.fromJson<User>("""{"name":"Tom","age":null}""", User::class.java)
// El valor null de age se eliminará automáticamente
```

Al deserializar JSON, los campos con valor `null` se asignan directamente a las propiedades del objeto, lo que puede causar referencias nulas en Kotlin. `addNotNullAdapter` elimina todos los valores `null` de objetos y arrays JSON — incluidas estructuras anidadas — para que no necesites comprobar nulos manualmente en cada acceso.

## Notas

- Las cadenas JSON vacías lanzan `IllegalStateException`.
- `toObject` soporta inferencia de tipos genéricos.
- `addNotNullAdapter` devuelve una instancia `Gson` modificada — reutilízala cuando sea posible.

## Código fuente

[AppGson.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppGson.kt)