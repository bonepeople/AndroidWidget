Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# LibraryLog

## Introducción

`LibraryLog` permite que una biblioteca usada como dependencia registre lo que está haciendo, para que el desarrollador de la app pueda ver cómo se ejecuta. La biblioteca crea un `LibraryLogEvent` en el hilo llamador y lo encola mediante `AppEvent.postAsync`.

La biblioteca solo publica el registro de ejecución. La app se suscribe a `LibraryLogEvent` y decide cómo presentarlo, por ejemplo en Logcat, en un archivo o mediante una subida.

Usa `AppLog` para los registros de negocio propios de la app.

## Uso

### Registrar

Una biblioteca puede usar un tag por módulo y registrar la actividad de ejecución en las rutas importantes.

```kotlin
val log = LibraryLog.tag("Network")
log.debug("request started")
log.error("request failed", exception)
```

`verbose`, `debug`, `info`, `warn` y `error` reciben `message: Any?` y un `throwable` opcional. El mensaje se convierte en texto antes de encolar el evento. `null` se convierte en `"null"`.

```kotlin
LibraryLog.defaultLog.info("cache ready")
```

El mismo tag devuelve siempre la misma instancia. `message` contiene solo el texto pasado a la llamada y el nombre del hilo se guarda en `threadName`. Cada llamada registra en el hilo llamador la ubicación de quien invoca: `className`, `methodName` y `lineNumber`. Si no existe el frame esperado se usan `UnknownClass`, `UnknownMethod` y `0`. El `stackOffset` predeterminado es 4 para una llamada directa; auméntalo cuando la llamada al log esté envuelta.

```kotlin
log.stackOffset = 5
log.debug("with context")
```

### Suscribirse

El desarrollador de la app se suscribe al mismo broadcast de `AppEvent` y recibe los registros de ejecución publicados por las bibliotecas. Cada suscriptor activo recibe todos los eventos. Como `replay = 0`, no se reciben eventos publicados antes de iniciar la recolección.

```kotlin
AppEvent.register(myScope) { event ->
    if (event !is LibraryLogEvent) return@register
    // Presentar el registro de ejecución de la biblioteca.
}
```

También se puede usar `AppEvent.register(owner, minState, action)` para crear una suscripción vinculada al ciclo de vida del `LifecycleOwner`.

## Reglas

- Procesa un evento cada vez dentro del callback cuando importe el orden. No inicies una corrutina nueva por cada línea.
- Un suscriptor lento puede suspender la entrega de `AppEvent` y hacer crecer su cola ilimitada; esta contrapresión está aceptada.
- Las excepciones del callback siguen la política del ámbito proporcionado. La app es propietaria del ámbito y debe elegir un `SupervisorJob`, instalar un `CoroutineExceptionHandler` o capturar las excepciones cuando sea necesario.
- No incluyas contraseñas, tokens ni otros datos privados en un log. `toString()` y la captura de la pila se ejecutan en el hilo llamador.
- No llames a `LibraryLog` incondicionalmente desde un suscriptor de logs; se produciría un bucle de realimentación infinito.
- Cancelar el `CoroutineScope` o el `Job` devuelto por `register` termina esa suscripción.

## Código fuente

- [LibraryLog.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/LibraryLog.kt)
- [LibraryLogEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/LibraryLogEvent.kt)
- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)