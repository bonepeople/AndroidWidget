Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# ActivityHolder

## Introducción

`ActivityHolder` es un objeto utilitario para gestionar el ciclo de vida, el estado y los datos en memoria de todas las instancias activas de `Activity` en una aplicación Android.

## Casos de uso

- Acceso global a la actividad actual
- Comunicación entre actividades
- Lógica de limpieza con conocimiento del ciclo de vida

## Características

- Obtener la actividad superior (actualmente visible)
- Almacenar y recuperar datos temporales clave-valor por instancia de actividad
- Acceder a la lista ordenada de todas las actividades activas

## Uso

### Acceso a la actividad superior

```kotlin
val currentActivity = ActivityHolder.getTopActivity()
```

### Datos temporales de actividad

Guardar datos:

```kotlin
ActivityHolder.putData(activity, "key", value)
```

Recuperar datos:

```kotlin
val value = ActivityHolder.getData(activity, "key")
```

Eliminar datos:

```kotlin
ActivityHolder.removeData(activity, "key")
```

### Todas las actividades activas

```kotlin
val allActivities = ActivityHolder.activityList
```

Mantiene el orden en que se abrieron las actividades.

## Código fuente

[ActivityHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ActivityHolder.kt)