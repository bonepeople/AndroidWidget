Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppToast

## Introducción

`AppToast` muestra mensajes toast desde cualquier parte de la aplicación. Cambia automáticamente al hilo principal e ignora mensajes en blanco.

## Características

- Soporte de duración corta y larga
- Visualización segura en hilos mediante corrutinas
- Filtrado automático de mensajes en blanco

## Uso

Mostrar un toast de duración corta:

```kotlin
AppToast.show("Operation completed successfully")
```

Mostrar un toast de duración larga:

```kotlin
AppToast.show("This message will stay longer", Toast.LENGTH_LONG)
```

## Notas

- Se ejecuta en el hilo principal usando `CoroutinesHolder.main.launch`.

## Código fuente

[AppToast.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppToast.kt)