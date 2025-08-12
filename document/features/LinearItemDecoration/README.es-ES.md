Versiones de idioma: [English](./README.md) | [中文](./README.zh-CN.md)

# LinearItemDecoration

## Introducción

`LinearItemDecoration` añade espaciado personalizable y divisores de color opcionales entre elementos en un `RecyclerView` basado en `LinearLayoutManager`. Soporta orientaciones vertical y horizontal.

## Características

- Espaciado entre elementos configurable (en dp)
- Color de divisor y relleno inicial/final opcionales

## Uso

Constructor:

```kotlin
LinearItemDecoration(space: Float)
```

- `space`: espaciado entre elementos (en dp)

Configuración opcional:

```kotlin
decoration.setColor(Color.GRAY)
decoration.setPadding(16f, 16f)
```

- `setColor`: color del divisor (ARGB)
- `setPadding`: relleno inicial y final del divisor (en dp)

Ejemplo:

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
val decoration = LinearItemDecoration(8f)
    .setColor(Color.LTGRAY)
    .setPadding(12f, 12f)

recyclerView.layoutManager = LinearLayoutManager(context)
recyclerView.addItemDecoration(decoration)
```

## Notas

Al usar `DiffUtil` o llamar `notifyItemInserted` / `notifyItemRemoved`, los divisores pueden no renderizarse correctamente.

Tras actualizar los datos, dispara manualmente una actualización parcial:

```kotlin
adapter.notifyItemRangeChanged(0, 2)
```

## Código fuente

[LinearItemDecoration.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/LinearItemDecoration.kt)