Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# GridItemDecoration

## Introduction

`GridItemDecoration` adds consistent spacing between items in a `RecyclerView` configured with a grid layout. It supports `GridLayoutManager` and `StaggeredGridLayoutManager` in both vertical and horizontal orientations.

## Features

- Configurable horizontal and vertical spacing (in dp)
- Works with grid and staggered grid layouts

## Usage

Constructor:

```kotlin
GridItemDecoration(horizontal: Float, vertical: Float)
```

- `horizontal`: horizontal spacing between grid items (in dp)
- `vertical`: vertical spacing between grid items (in dp)

Example:

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
recyclerView.layoutManager = GridLayoutManager(context, 3)
recyclerView.addItemDecoration(GridItemDecoration(8f, 12f))
```

This sets up a 3-column grid with 8dp horizontal spacing and 12dp vertical spacing between items.

## Notes

When updating the adapter via `DiffUtil`, or calling `notifyItemInserted` / `notifyItemRemoved`, dividers may not update properly.

After updating the data set, also call:

```kotlin
adapter.notifyItemRangeChanged(0, itemCount)
```

This forces the RecyclerView to recalculate spacing for all items.

## Source Code

[GridItemDecoration.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/GridItemDecoration.kt)