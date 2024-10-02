Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppRandom

**Código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppRandom.kt  
**Este documento fue creado con la asistencia de ChatGPT.**

## Descripción general

`AppRandom` es un objeto utilitario que proporciona métodos convenientes para generar valores aleatorios, como cadenas de texto, números enteros y listas desordenadas. Usa una semilla basada en la hora actual del sistema.

> Es útil para generar datos de prueba, crear identificadores únicos o proporcionar experiencias de usuario aleatorias.

## Cómo usarlo

### 1. Generar una cadena aleatoria

Crea una cadena con letras (mayúsculas y minúsculas) y dígitos:

```kotlin
val cadena = AppRandom.randomString(12)
// Ejemplo: "aZ3bD9x1YqT8"
```

- El parámetro `length` define la cantidad de caracteres de la cadena resultante.

### 2. Generar un entero aleatorio dentro de un rango

Devuelve un número entero aleatorio dentro del rango especificado:

```kotlin
val numero = AppRandom.randomInt(10..99)
// Ejemplo: 47
```

- Si el rango está vacío, se devuelve `0`.

### 3. Barajar una lista

Devuelve una nueva lista con los elementos desordenados:

```kotlin
val lista = listOf(1, 2, 3, 4, 5)
val desordenada = AppRandom.shuffleList(lista)
// Ejemplo: [3, 1, 5, 2, 4]
```

- La lista original no se modifica.