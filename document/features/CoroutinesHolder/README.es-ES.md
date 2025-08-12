Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# CoroutinesHolder

## Introducción

`CoroutinesHolder` proporciona ámbitos globales singleton de corrutinas para los `Dispatchers` más usados — `Default`, `Main` e `IO`. Permite lanzar corrutinas sin crear ámbitos manualmente.

## Casos de uso

- Funciones utilitarias o módulos de biblioteca que necesitan un ámbito compartido
- Trabajo en segundo plano desde contextos sin ciclo de vida (p. ej. `AppEvent.postAsync`, `AppToast.show`)

## Características

- Instancias listas de `CoroutineScope` para `Default`, `Main` e `IO`
- Cada ámbito tiene su propio `Job` independiente

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

## Notas

- Prefiere `lifecycleScope` para componentes UI que necesiten conocimiento del ciclo de vida.
- Las tareas lanzadas aquí no se cancelan automáticamente al destruir una Activity o Fragment.

## Código fuente

[CoroutinesHolder.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/CoroutinesHolder.kt)