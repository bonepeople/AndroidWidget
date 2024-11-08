Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# ActivityHolder

**Enlace al código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/ActivityHolder.kt

`ActivityHolder` es un objeto utilitario que permite gestionar el ciclo de vida, estado y datos asociados a todas las instancias activas de `Activity` dentro de una aplicación Android.

> 📄 Esta documentación fue asistida por ChatGPT.

## Uso

### Obtener la Actividad Superior

Puedes recuperar en cualquier momento la actividad actualmente visible:

```kotlin
val currentActivity = ActivityHolder.getTopActivity()
```

### Almacenar Datos Temporales

Permite guardar datos temporales tipo clave-valor asociados a una instancia específica de actividad.

#### Guardar datos:

```kotlin
ActivityHolder.putData(activity, "key", value)
```

#### Obtener datos:

```kotlin
val value = ActivityHolder.getData(activity, "key")
```

#### Eliminar datos:

```kotlin
ActivityHolder.removeData(activity, "key")
```

### Obtener Todas las Actividades Activas

```kotlin
val allActivities = ActivityHolder.activityList
```

La lista mantiene el orden en que se abrieron las actividades.

## Notas

- Es útil para acceso global, comunicación entre actividades o lógica de limpieza.