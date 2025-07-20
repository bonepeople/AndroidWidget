Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# AppEvent

**Enlaces al código fuente**:
- [AppEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEvent.kt)
- [AppEventData.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/AppEventData.kt)
- [CommonEvent.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/util/event/CommonEvent.kt)

`AppEvent` es un bus de eventos global con conocimiento del ciclo de vida, basado en `MutableSharedFlow`. Publica eventos desde cualquier lugar y suscríbete en un `LifecycleOwner` (p. ej. `Activity`, `Fragment`). La entrega se pausa cuando el ciclo de vida está por debajo del estado mínimo configurado, y la suscripción se cancela al destruirse.

> 📄 Esta documentación fue asistida por ChatGPT.

## Uso

### 1. Definir eventos

Implementa la interfaz marcadora `AppEventData`. Se recomiendan eventos tipados con `data class`; también puedes usar `CommonEvent` para señales genéricas.

```kotlin
// Evento tipado
data class TabChangedEvent(val index: Int) : AppEventData

// Evento genérico solo señal
CommonEvent("refresh")

// Evento genérico con payload
CommonEvent("user_login", userId)
```

### 2. Suscribirse

Llama a `register` en un `LifecycleOwner` y maneja los eventos con `when`:

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

Por defecto, los eventos se entregan solo cuando el ciclo de vida está al menos en `STARTED`. Pasa `minState` para recibirlos antes:

```kotlin
AppEvent.register(this, minState = Lifecycle.State.CREATED) { event ->
    // ...
}
```

### 3. Publicar eventos

Usa `post` dentro de una corrutina; usa `postAsync` desde contextos que no sean corrutinas (callbacks, listeners de clic, etc.):

```kotlin
// Dentro de una corrutina
lifecycleScope.launch {
    AppEvent.post(TabChangedEvent(0))
}

// Desde cualquier hilo / contexto no corrutina
AppEvent.postAsync(TabChangedEvent(0))
AppEvent.postAsync(CommonEvent("refresh"))
```

`postAsync` publica en un hilo en segundo plano mediante el ámbito `default` de [CoroutinesHolder](../CoroutinesHolder/README.es-ES.md); los callbacks de suscripción siguen ejecutándose en el hilo principal vía `lifecycleScope`.

## Resumen de API

| Método | Descripción |
|--------|-------------|
| `register(owner, minState, action)` | Suscripción ligada al ciclo de vida; `action` es una función suspend |
| `post(event)` | Publica un evento desde una corrutina |
| `postAsync(event)` | Publica un evento de forma asíncrona desde un contexto no corrutina |

## Notas

- **No pegajoso**: Los eventos publicados antes de registrarse no se reenvían; solo se reciben eventos nuevos tras suscribirse.
- **Solo en proceso**: Los objetos de evento se pasan por referencia dentro del mismo proceso; no hay entrega entre procesos.
- **Seguro con el ciclo de vida**: Las suscripciones se cancelan automáticamente al destruirse el `Activity` / `Fragment`; no hace falta desregistrar manualmente.
- **Política de búfer**: Usa desbordamiento `DROP_OLDEST`; bajo carga extrema, los eventos más antiguos pueden descartarse.
- **Filtrado por tipo**: Cada suscriptor recibe cada evento publicado; filtra con `when`. Los eventos tipados son más fáciles de mantener que `CommonEvent`.
