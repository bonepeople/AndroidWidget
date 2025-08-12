Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# LinearItemDecoration

## Introduction

`LinearItemDecoration` adds customizable spacing and optional color dividers between items in a `LinearLayoutManager`-based `RecyclerView`. It supports both vertical and horizontal orientations.

## Features

- Configurable item spacing (in dp)
- Optional divider color and start/end padding

## Usage

Constructor:

```kotlin
LinearItemDecoration(space: Float)
```

- `space`: spacing between items (in dp)

Optional configuration:

```kotlin
decoration.setColor(Color.GRAY)
decoration.setPadding(16f, 16f)
```

- `setColor`: divider color (ARGB)
- `setPadding`: start and end padding for the divider (in dp)

Example:

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
val decoration = LinearItemDecoration(8f)
    .setColor(Color.LTGRAY)
    .setPadding(12f, 12f)

recyclerView.layoutManager = LinearLayoutManager(context)
recyclerView.addItemDecoration(decoration)
```

## Notes

When using `DiffUtil` or calling `notifyItemInserted` / `notifyItemRemoved`, dividers may not render correctly.

After updating the data, manually trigger a partial refresh:

```kotlin
adapter.notifyItemRangeChanged(0, 2)
```

## Source Code

[LinearItemDecoration.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/LinearItemDecoration.kt)