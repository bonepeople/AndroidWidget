Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# GridItemDecoration

## Introducción

`GridItemDecoration` añade espaciado uniforme entre elementos en un `RecyclerView` configurado con diseño de cuadrícula. Soporta `GridLayoutManager` y `StaggeredGridLayoutManager` en orientaciones vertical y horizontal.

## Características

- Espaciado horizontal y vertical configurable (en dp)
- Funciona con diseños de cuadrícula y cuadrícula escalonada

## Uso

Constructor:

```kotlin
GridItemDecoration(horizontal: Float, vertical: Float)
```

- `horizontal`: espaciado horizontal entre elementos de cuadrícula (en dp)
- `vertical`: espaciado vertical entre elementos de cuadrícula (en dp)

Ejemplo:

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
recyclerView.layoutManager = GridLayoutManager(context, 3)
recyclerView.addItemDecoration(GridItemDecoration(8f, 12f))
```

Configura una cuadrícula de 3 columnas con 8dp de espaciado horizontal y 12dp vertical.

## Notas

Al actualizar el adaptador mediante `DiffUtil`, o al llamar `notifyItemInserted` / `notifyItemRemoved`, los divisores pueden no actualizarse correctamente.

Tras actualizar el conjunto de datos, llama también:

```kotlin
adapter.notifyItemRangeChanged(0, itemCount)
```

Para forzar al RecyclerView a recalcular el espaciado de todos los elementos.

## Código fuente

[GridItemDecoration.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/GridItemDecoration.kt)