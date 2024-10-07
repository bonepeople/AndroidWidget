Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppToast

**Código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppToast.kt  
**Este documento fue creado con la asistencia de ChatGPT.**

## Descripción general

`AppToast` es una clase utilitaria para mostrar mensajes Toast desde cualquier parte de tu aplicación, gestionando automáticamente el hilo principal mediante corrutinas.

> Ideal para mostrar notificaciones rápidas al usuario sin preocuparse del contexto de hilo.

## Cómo usarlo

### 1. Mostrar un Toast de duración corta

```kotlin
AppToast.show("Operación completada con éxito")
```

### 2. Mostrar un Toast de duración larga

```kotlin
AppToast.show("Este mensaje se mostrará por más tiempo", Toast.LENGTH_LONG)
```

## Notas

- Ignora automáticamente mensajes nulos o vacíos.
- Asegura la ejecución en el hilo principal usando `CoroutinesHolder.main.launch`.