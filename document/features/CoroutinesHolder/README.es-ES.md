Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# CoroutinesHolder

**Enlace al código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/CoroutinesHolder.kt

`CoroutinesHolder` proporciona instancias globales de `CoroutineScope` para los `Dispatchers` más utilizados: `Default`, `Main` e `IO`. Esto facilita el lanzamiento de corutinas en toda la aplicación sin tener que crear ámbitos manualmente.

> 📄 Esta documentación fue asistida por ChatGPT.

## Uso

Usa el ámbito adecuado según el tipo de tarea:

### Tareas de procesamiento (Dispatcher Default):

```kotlin
CoroutinesHolder.default.launch {
    // Cálculos intensivos en CPU
}
```

### Tareas relacionadas con la UI (Dispatcher Main):

```kotlin
CoroutinesHolder.main.launch {
    // Actualizar la interfaz de usuario
}
```

### Tareas de entrada/salida (Dispatcher IO):

```kotlin
CoroutinesHolder.io.launch {
    // Leer/escribir archivos, llamadas de red
}
```

Cada ámbito tiene su propio `Job`, por lo tanto son cancelables de forma independiente (aunque esta clase no proporciona métodos para hacerlo directamente).

## Notas

- Útil en funciones utilitarias o módulos de biblioteca donde no quieres crear scopes adicionales.
- Si necesitas tareas con conocimiento del ciclo de vida, considera usar `lifecycleScope`.