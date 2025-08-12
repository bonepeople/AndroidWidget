Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppRandom

## Introducción

`AppRandom` proporciona métodos convenientes para generar cadenas aleatorias, enteros y listas mezcladas. Usa una semilla basada en la hora del sistema, garantizando aleatoriedad y permitiendo reproducibilidad cuando sea necesario.

## Casos de uso

- Generar datos de prueba
- Crear identificadores únicos
- Aleatorizar experiencias de usuario

## Características

- Cadenas alfanuméricas aleatorias de longitud configurable
- Enteros aleatorios dentro de un rango especificado
- Copias mezcladas de colecciones (la lista original no se modifica)

## Uso

Generar una cadena aleatoria (dígitos, letras minúsculas y mayúsculas):

```kotlin
val str = AppRandom.randomString(12)
// Ejemplo: "aZ3bD9x1YqT8"
```

Generar un entero aleatorio en un rango:

```kotlin
val number = AppRandom.randomInt(10..99)
// Ejemplo: 47
```

Devuelve `0` si el rango está vacío.

Mezclar una lista:

```kotlin
val list = listOf(1, 2, 3, 4, 5)
val shuffled = AppRandom.shuffleList(list)
// Ejemplo: [3, 1, 5, 2, 4]
```

## Código fuente

[AppRandom.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppRandom.kt)