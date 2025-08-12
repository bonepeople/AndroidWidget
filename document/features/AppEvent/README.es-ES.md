Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppEvent

## Introducción

`AppEvent` es un bus de eventos global con conocimiento del ciclo de vida, basado en `MutableSharedFlow`. Publica eventos desde cualquier lugar; suscríbete en un `LifecycleOwner` (p. ej. `Activity`, `Fragment`).

**La publicación es independiente del ciclo de vida del suscriptor** — `post` / `postAsync` siempre emiten al flujo global. **La recepción depende del ciclo de vida** — un suscriptor recoge eventos solo cuando su ciclo de vida está al menos en `minState`; al caer por debajo, deja de recibir y los eventos emitidos durante el periodo inactivo no se reproducen al reanudarse. La suscripción se cancela al destruirse.

## Características

- Entrega de eventos no pegajosa
- Eventos tipados `data class` o señales genéricas `CommonEvent`
- Modos de publicación `post` (corrutina) y `postAsync` (no corrutina)
- Limpieza automática de suscripciones al destruir el ciclo de vida

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

`postAsync` se despacha mediante el ámbito `default` de [CoroutinesHolder](../CoroutinesHolder/README.es-ES.md) en un hilo en segundo plano; los callbacks del suscriptor siguen ejecutándose en el hilo principal vía `lifecycleScope`.

### Resumen de API

| Método | Descripción |
|--------|-------------|
| `register(owner, minState, action)` | Suscripción vinculada al ciclo de vida; `action` es una función suspend |
| `post(event)` | Publicar un evento desde una corrutina |
| `postAsync(event)` | Publicar un evento de forma asíncrona desde un contexto no corrutina |

## Notas

- **No pegajoso**: Los eventos publicados antes del registro no se reproducen. Los eventos publicados mientras un suscriptor está inactivo (ciclo de vida por debajo de `minState`) tampoco se reproducen al reanudarse — solo se reciben eventos publicados después.
- **Publicación vs recepción**: Los emisores no se ven afectados por el ciclo de vida de ningún suscriptor. Otros suscriptores que sigan activos reciben eventos con normalidad.
- **Solo en proceso**: Los objetos de evento se pasan por referencia dentro del mismo proceso; no hay entrega entre procesos.
- **Seguro con ciclo de vida**: Las suscripciones se cancelan automáticamente al destruir la `Activity` / `Fragment`; no hace falta desregistrar manualmente.
- **Política de búfer**: Usa manejo de desbordamiento `DROP_OLDEST`; bajo carga extrema, pueden descartarse eventos antiguos.
- **Filtrado por tipo**: Cada suscriptor recibe todos los eventos publicados; filtra con `when`. Los eventos tipados son más fáciles de mantener que `CommonEvent`.

## Código fuente

- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)
- [AppEventData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEventData.kt)
- [CommonEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/CommonEvent.kt)