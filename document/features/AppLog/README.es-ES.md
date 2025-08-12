Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppLog

## Introducción

`AppLog` es una utilidad de registro ligera para Android. Envuelve la clase nativa `Log` y añade información de hilo, ubicación del llamador e instancias reutilizables por tag.

## Casos de uso

- Registro por módulo o funcionalidad con tags personalizados
- Incluir hilo o ubicación del método para depuración
- Configuración de registro centralizada y controlable

## Características

- Cinco niveles de log: `verbose`, `debug`, `info`, `warn`, `error`
- Activación global y por instancia
- Visualización opcional del nombre del hilo y ubicación del llamador
- Instancias reutilizables por tag

## Uso

Obtener el logger predeterminado:

```kotlin
AppLog.defaultLog.debug("App started.")
```

Crear un logger con un tag específico:

```kotlin
val log = AppLog.tag("MainActivity")
log.info("View created")
```

Usar distintos niveles de log:

```kotlin
log.verbose("This is a verbose message")
log.debug("This is a debug message")
log.info("This is an info message")
log.warn("This is a warning")
log.error("This is an error")
```

Activar información de hilo y pila de llamadas:

```kotlin
log.showThreadInfo = true
log.showStackInfo = true
log.stackOffset = 6 // Ajustar según el nivel de la pila
log.debug("With stack and thread info") // [main] With stack and thread info @ MainActivity$startTest$1.invokeSuspend:38
```

Controlar el registro global:

```kotlin
AppLog.enable = false // Desactivar todos los logs globalmente
```

## Notas

- Ajusta `stackOffset` según la profundidad de llamadas de tu método.
- `AppLog.enable` es un interruptor global; `AppLog.tag(...).enable` es a nivel de instancia.
- Evita logs de depuración excesivos en builds de producción por rendimiento.

## Código fuente

[AppLog.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/AppLog.kt)