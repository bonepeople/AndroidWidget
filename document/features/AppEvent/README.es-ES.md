Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppEvent

## Introducción

`AppEvent` es un bus de eventos global basado en un `Channel` ilimitado y un `MutableSharedFlow`. Publica eventos desde cualquier lugar; suscríbete en un `LifecycleOwner` (p. ej. `Activity`, `Fragment`) o en cualquier `CoroutineScope`.

**La publicación es independiente del ciclo de vida del suscriptor** — `post` / `postAsync` siempre encolan eventos para el flujo global. **La recepción vinculada al ciclo de vida depende de `minState`** — al caer por debajo de ese estado, el suscriptor deja de recibir y los eventos emitidos durante el periodo inactivo no se reproducen al reanudarse. Un suscriptor de ámbito permanece activo hasta que se cancela su ámbito o el `Job` devuelto.

## Características

- Entrega de eventos no pegajosa
- Eventos tipados `data class` o señales genéricas `CommonEvent`
- Modos de publicación `post` (corrutina) y `postAsync` (no corrutina)
- Limpieza automática de suscripciones al destruir el ciclo de vida
- Suscripción mediante `CoroutineScope` para trabajos que deben permanecer activos en segundo plano

## Uso

### Definir eventos

Implementa la interfaz marcadora `AppEventData`. Prefiere eventos tipados `data class`; usa `CommonEvent` para señales genéricas.

```kotlin
// Evento tipado
data class TabChangedEvent(val index: Int) : AppEventData

// Señal genérica
CommonEvent("refresh")

// Evento genérico con carga
CommonEvent("user_login", userId)
```

### Suscribirse

Llama `register` en un `LifecycleOwner` y gestiona eventos con `when`:

```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppEvent.register(this) { event ->
            when (event) {
                is TabChangedEvent -> switchTab(event.index)
                is CommonEvent -> {
                    when (event.name) {
                        "refresh" -> reloadData()
                        "user_login" -> onUserLogin(event.data)
                    }
                }
            }
        }
    }
}
```

Por defecto, un suscriptor recibe eventos solo cuando su ciclo de vida está al menos en `STARTED`. Pasa `minState` para recibirlos antes:

```kotlin
AppEvent.register(this, minState = Lifecycle.State.CREATED) { event ->
    // ...
}
```

Suscríbete sin ciclo de vida cuando el recolector deba seguir activo en segundo plano. La recolección comienza antes de que vuelva `register`. Cancela el ámbito o el `Job` devuelto para detenerla:

```kotlin
val job = AppEvent.register(myScope) { event ->
    if (event is CommonEvent && event.name == "refresh") {
        reloadData()
    }
}
```

`LibraryLogEvent` es un registro de ejecución publicado por una biblioteca usada como dependencia. Consulta [LibraryLog](../LibraryLog/README.es-ES.md).

### Publicar eventos

Usa `post` dentro de una corrutina; usa `postAsync` desde contextos no corrutina (callbacks, listeners de clic, etc.):

```kotlin
// Dentro de una corrutina
lifecycleScope.launch {
    AppEvent.post(TabChangedEvent(0))
}

// Desde cualquier hilo / contexto no corrutina
AppEvent.postAsync(TabChangedEvent(0))
AppEvent.postAsync(CommonEvent("refresh"))
```

`postAsync` solo llama a `Channel.trySend` y vuelve tras el intento de encolado, sin esperar a que los suscriptores terminen. Después, un único consumidor en el ámbito `default` de [CoroutinesHolder](../CoroutinesHolder/README.es-ES.md) emite al flujo compartido. Los suscriptores con ciclo de vida recogen en el hilo principal vía `lifecycleScope`. Un suscriptor de ámbito recoge en el despachador del ámbito proporcionado.

### Resumen de API

| Método | Descripción |
|--------|-------------|
| `register(owner, minState, action)` | Suscripción vinculada al ciclo de vida; `action` es una función suspend |
| `register(scope, action)` | Suscripción vinculada a un ámbito. Devuelve un `Job`. La recolección comienza antes de que vuelva la llamada |
| `post(event)` | Publicar un evento desde una corrutina |
| `postAsync(event)` | Encolar un evento desde un contexto no corrutina. Vuelve tras `trySend`, no después de que terminen los suscriptores |

## Notas

- **No pegajoso**: Los eventos publicados antes del registro no se reproducen. Los eventos publicados mientras un suscriptor está inactivo (ciclo de vida por debajo de `minState`) tampoco se reproducen al reanudarse — solo se reciben eventos publicados después.
- **Publicación vs recepción**: Los emisores no se ven afectados por el ciclo de vida de ningún suscriptor. Los suscriptores con ciclo de vida solo reciben mientras están activos; los suscriptores de ámbito reciben hasta que se cancelan.
- **Solo en proceso**: Los objetos de evento se pasan por referencia dentro del mismo proceso; no hay entrega entre procesos.
- **Duración de la suscripción**: Una suscripción con ciclo de vida se cancela al destruirse su `Activity` / `Fragment`. Una suscripción de ámbito termina cuando se cancela su `CoroutineScope` o el `Job` devuelto.
- **Política de excepciones del ámbito**: Las excepciones de un suscriptor de ámbito siguen la política de propagación y manejo del ámbito proporcionado. La app debe elegir un `SupervisorJob`, instalar un `CoroutineExceptionHandler` o capturar las excepciones cuando sea necesario.
- **Cola y búfer**: La cola es `Channel.UNLIMITED`. El flujo compartido usa `replay = 0`, `extraBufferCapacity = 64` y `onBufferOverflow = SUSPEND`. Un suscriptor lento puede suspender al único consumidor y hacer crecer la cola ilimitada. No se descartan eventos. Esta contrapresión es intencional.
- **Orden**: Los suscriptores que necesiten respetar el orden de encolado deben procesar cada evento directamente en el callback. No inicies una corrutina nueva por cada evento.
- **Filtrado por tipo**: Cada suscriptor recibe todos los eventos publicados; filtra con `when`. Los eventos tipados son más fáciles de mantener que `CommonEvent`.

## Código fuente

- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)
- [AppEventData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEventData.kt)
- [CommonEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/CommonEvent.kt)