Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# CoroutinesHolder

## Introducción

`CoroutinesHolder` proporciona ámbitos globales singleton de corrutinas para los `Dispatchers` más usados — `Default`, `Main` e `IO` — además de ejecución serial por nombre. Permite lanzar corrutinas sin crear ámbitos manualmente.

## Casos de uso

- Funciones utilitarias o módulos de biblioteca que necesitan un ámbito compartido
- Trabajo en segundo plano desde contextos sin ciclo de vida (p. ej. `AppEvent.postAsync`, `AppToast.show`)
- Garantizar que un mismo recurso (caché, base de datos, archivo) solo sea accedido por una corrutina a la vez

## Características

- Instancias listas de `CoroutineScope` para `Default`, `Main` e `IO`
- Cada ámbito tiene su propio `SupervisorJob` independiente; un hijo fallido no cancela a los demás del mismo ámbito
- Ejecución serial por nombre con Mutex: `runSerial`, `launchSerial`, `asyncSerial`

## Elegir una API

| API | Cuándo usar | Dispatcher |
|-----|-------------|------------|
| `runSerial` | Ya dentro de una corrutina | Sigue el contexto de la corrutina llamante |
| `launchSerial` | Sin corrutina existente, sin valor de retorno | Determinado por `scope` (por defecto `default`) |
| `asyncSerial` | Sin corrutina existente, se necesita resultado | Determinado por `scope` (por defecto `default`) |

## Uso

Tareas intensivas en CPU:

```kotlin
CoroutinesHolder.default.launch {
    // Realizar cálculos pesados
}
```

Tareas relacionadas con UI:

```kotlin
CoroutinesHolder.main.launch {
    // Actualizar UI de forma segura
}
```

Tareas intensivas en IO:

```kotlin
CoroutinesHolder.io.launch {
    // Leer/escribir archivos, llamadas de red
}
```

Tareas seriales (el mismo `name` no se ejecuta en paralelo; distintos nombres son independientes):

```kotlin
// Dentro de una corrutina existente; no cambia de dispatcher, se ejecuta en IO vía io.launch
CoroutinesHolder.io.launch {
    CoroutinesHolder.runSerial("cache") {
        updateCache()
    }
}

// Lanzar sin un ámbito de corrutina existente; pasar scope = io explícitamente
CoroutinesHolder.launchSerial("db", scope = CoroutinesHolder.io) {
    writeToDb()
}

// Cuando se necesita un resultado; llamar await() para obtenerlo
val deferred = CoroutinesHolder.asyncSerial("file", scope = CoroutinesHolder.io) {
    readFile()
}
val content = deferred.await()
```

## Notas

- El parámetro `name` de `runSerial`, `launchSerial` y `asyncSerial` es obligatorio y no puede estar en blanco; de lo contrario se lanza `IllegalArgumentException`.
- Usa nombres fijos con significado (p. ej. `"cache"`, `"db"`); no construyas nombres dinámicamente, o el `Mutex` permanecerá en memoria de forma permanente.
- La exclusión serial solo se aplica a nivel de corrutina; la ejecución no está ligada a un hilo físico concreto.
- **No anides llamadas seriales con el mismo `name` en la misma corrutina.** El `Mutex` subyacente no es reentrante y provocará un interbloqueo.
  El `block` de `launchSerial` / `asyncSerial` ya se ejecuta dentro de `runSerial`, por lo que llamar a `runSerial` con el mismo nombre dentro del bloque también provoca interbloqueo:

```kotlin
// Incorrecto — provocará interbloqueo
CoroutinesHolder.runSerial("cache") {
    CoroutinesHolder.runSerial("cache") { updateCache() }
}

// Incorrecto — el block ya está dentro de runSerial("db"); otro runSerial con el mismo nombre provoca interbloqueo
CoroutinesHolder.launchSerial("db", scope = CoroutinesHolder.io) {
    CoroutinesHolder.runSerial("db") { writeToDb() }
}
```

> Nota: Llamar a otro `launchSerial` con el mismo nombre dentro del bloque no provoca interbloqueo (inicia una nueva corrutina), pero suele ser un uso no intencionado. Esperar al hijo con `await()` / `join()` sí provocará interbloqueo.

- `runSerial` no cambia de dispatcher; la planificación sigue el contexto de la corrutina llamante.
- `launchSerial` / `asyncSerial` usan por defecto el ámbito `default` (`Dispatchers.Default`); pasa `scope = io` o `scope = main` para trabajo IO o UI.
- Prefiere `lifecycleScope` para componentes UI que necesiten conocimiento del ciclo de vida.
- Las tareas lanzadas aquí no se cancelan automáticamente al destruir una Activity o Fragment.

## Código fuente

[CoroutinesHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/CoroutinesHolder.kt)