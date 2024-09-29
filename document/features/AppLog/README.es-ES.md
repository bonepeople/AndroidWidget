Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# Guía de uso de AppLog

> Este documento fue elaborado con la ayuda de ChatGPT  
> Enlace al código fuente: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppLog.kt

## Introducción

`AppLog` es una utilidad de registro para Android que envuelve la clase `Log` nativa y agrega funcionalidades como mostrar el nombre del hilo, ubicación del método, y soporte para múltiples instancias reutilizables por etiqueta (`tag`).

## Características

- Cinco niveles de log: `verbose`, `debug`, `info`, `warn`, `error`;
- Habilitación global o por instancia;
- Opción para mostrar el nombre del hilo actual;
- Opción para mostrar clase, método y número de línea;
- Instancias reutilizables basadas en etiquetas.

## Cómo usar

### 1. Usar el registrador por defecto

```kotlin
AppLog.defaultLog.debug("App started.")
```

### 2. Crear una instancia con etiqueta personalizada

```kotlin
val log = AppLog.tag("MainActivity")
log.info("Vista creada")
```

### 3. Usar distintos niveles de log

```kotlin
log.verbose("Mensaje verbose")
log.debug("Mensaje de depuración")
log.info("Mensaje informativo")
log.warn("Advertencia")
log.error("Error")
```

### 4. Mostrar información del hilo y del método

```kotlin
log.showThreadInfo = true
log.showStackInfo = true
log.stackOffset = 6 // Ajustar según la profundidad de la pila
log.debug("Con información de hilo y método") // [main] Con información de hilo y método @ MainActivity$startTest$1.invokeSuspend:38
```

### 5. Controlar el registro globalmente

```kotlin
AppLog.enable = false // Desactiva todos los logs
```

## Casos recomendados de uso

- Registrar logs por módulo o clase mediante etiquetas;
- Incluir información detallada de depuración (método, hilo);
- Gestionar el comportamiento del log de manera centralizada.

## Notas

- Ajusta `stackOffset` para coincidir con la profundidad real del método;
- `AppLog.enable` controla el log global; `.enable` por instancia controla cada tag;
- Evita usar logs detallados en producción para mejorar el rendimiento.