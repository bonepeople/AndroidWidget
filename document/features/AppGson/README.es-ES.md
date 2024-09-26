Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppGson

> Este documento fue elaborado con la ayuda de ChatGPT  
> Enlace al código fuente: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppGson.kt

## Introducción

`AppGson` es una clase utilitaria para la serialización y deserialización de JSON basada en Gson. Mejora el comportamiento predeterminado eliminando automáticamente los valores `null` durante la deserialización, lo que mejora la seguridad en Kotlin.

## Características

- Proporciona una instancia `Gson` predeterminada que elimina valores `null`;
- Convierte objetos en cadenas JSON;
- Convierte cadenas JSON en objetos tipados mediante genéricos;
- Permite el uso de instancias personalizadas de `Gson`;
- Incluye `addNotNullAdapter` para agregar filtrado de nulls a cualquier instancia de `Gson`.

## Cómo usar

### 1. Convertir un objeto a una cadena JSON

```kotlin
val json = AppGson.toJson(User(name = "Tom", age = 25))
```

### 2. Convertir una cadena JSON a un objeto

```kotlin
val json = """{"name":"Tom","age":25}"""
val user: User = AppGson.toObject(json)
```

### 3. Usar una instancia Gson personalizada

```kotlin
val customGson = GsonBuilder().create()
val json = AppGson.toJson(data, customGson)
```

### 4. Usar `addNotNullAdapter` para filtrar valores `null`

```kotlin
val gson = AppGson.addNotNullAdapter(GsonBuilder().create())
val user = gson.fromJson<User>("""{"name":"Tom","age":null}""", User::class.java)
// El campo age será eliminado automáticamente
```

#### ¿Por qué usar `addNotNullAdapter`?

Cuando se analiza un JSON con campos `null`, esos valores se asignan directamente al objeto, lo que puede provocar errores o excepciones en Kotlin.

Al usar `addNotNullAdapter`, se añade un adaptador que elimina automáticamente los `null` de todos los niveles del JSON (objetos o listas). Esto evita tener que comprobar manualmente los nulos en tu código.

## Casos recomendados de uso

- Cuando deseas ignorar los campos `null` al analizar JSON;
- Cuando necesitas evitar `NullPointerException` en Kotlin;
- Cuando prefieres gestionar el comportamiento de JSON desde un único lugar.

## Notas

- Las cadenas JSON vacías lanzarán una `IllegalStateException`;
- `toObject` admite inferencia de tipos genéricos;
- `addNotNullAdapter` devuelve una instancia modificada de `Gson`, se recomienda reutilizarla.