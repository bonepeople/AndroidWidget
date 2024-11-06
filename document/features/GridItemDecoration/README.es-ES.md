Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# GridItemDecoration

**Enlace al código fuente**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/GridItemDecoration.kt

`GridItemDecoration` es una clase utilitaria que se utiliza para agregar espacios uniformes (divisores) entre los elementos de un RecyclerView con diseño de cuadrícula.

Es compatible tanto con `GridLayoutManager` como con `StaggeredGridLayoutManager`, y funciona en orientaciones verticales y horizontales.

> 📄 Esta documentación fue asistida por ChatGPT.

## Uso

### Constructor

```kotlin
GridItemDecoration(horizontal: Float, vertical: Float)
```

- `horizontal`: espacio horizontal entre los elementos (en dp).
- `vertical`: espacio vertical entre los elementos (en dp).

### Ejemplo

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
recyclerView.layoutManager = GridLayoutManager(contexto, 3)
recyclerView.addItemDecoration(GridItemDecoration(8f, 12f))
```

Esto configura una cuadrícula de 3 columnas con 8dp de espacio horizontal y 12dp de espacio vertical entre elementos.

## Problema Conocido

Al actualizar el adaptador con `DiffUtil`, o llamar manualmente a:

```kotlin
adapter.notifyItemInserted(position)
adapter.notifyItemRemoved(position)
```

... los divisores podrían no actualizarse correctamente.

### ✅ Solución

Después de actualizar la lista de datos, también llama a:

```kotlin
adapter.notifyItemRangeChanged(0, itemCount)
```

Esto obliga al RecyclerView a recalcular los espacios de todos los elementos.