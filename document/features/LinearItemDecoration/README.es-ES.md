Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# LinearItemDecoration

**Enlace al código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/LinearItemDecoration.kt

`LinearItemDecoration` agrega espacios personalizables y divisores opcionales entre los elementos de un RecyclerView con `LinearLayoutManager`.

Soporta orientación vertical y horizontal, y permite configurar color y padding para los divisores.

> 📄 Esta documentación fue asistida por ChatGPT.

## Uso

### Constructor

```kotlin
LinearItemDecoration(space: Float)
```

- `space`: espacio entre elementos (en dp).

### Configuraciones Opcionales

```kotlin
decoration.setColor(Color.GRAY)
decoration.setPadding(16f, 16f)
```

- `setColor`: define el color del divisor (formato ARGB).
- `setPadding`: define el padding inicial y final (en dp) del divisor.

### Ejemplo

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
val decoration = LinearItemDecoration(8f)
    .setColor(Color.LTGRAY)
    .setPadding(12f, 12f)

recyclerView.layoutManager = LinearLayoutManager(contexto)
recyclerView.addItemDecoration(decoration)
```

## Problema Conocido

Al utilizar `DiffUtil` o llamar a:

```kotlin
adapter.notifyItemInserted(position)
adapter.notifyItemRemoved(position)
```

...los divisores podrían no mostrarse correctamente.

### ✅ Solución

Después de actualizar los datos, realiza una actualización parcial:

```kotlin
adapter.notifyItemRangeChanged(0, 2)
```