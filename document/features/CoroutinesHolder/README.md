Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# CoroutinesHolder

**Link to source code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/CoroutinesHolder.kt

`CoroutinesHolder` provides convenient global singleton coroutine scopes for commonly used `Dispatchers` — `Default`, `Main`, and `IO`. This simplifies coroutine launching throughout your app by offering ready-to-use `CoroutineScope` instances.

> 📄 This documentation was assisted by ChatGPT.

## Usage

Access the appropriate scope based on task type:

### CPU-bound tasks (Default dispatcher):

```kotlin
CoroutinesHolder.default.launch {
    // Perform heavy computation
}
```

### UI-related tasks (Main dispatcher):

```kotlin
CoroutinesHolder.main.launch {
    // Update UI safely
}
```

### IO-bound tasks (IO dispatcher):

```kotlin
CoroutinesHolder.io.launch {
    // Read/write files, perform network calls
}
```

Each scope comes with its own `Job`, meaning they are independently cancelable if needed (though cancellation control is not exposed directly here).

## Notes

- Useful for utility functions or library modules where you want to avoid creating multiple scopes manually.
- Be cautious of leaking jobs if your tasks need lifecycle-awareness (use `lifecycleScope` for UI components).