Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# GridItemDecoration

**Link to source code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/GridItemDecoration.kt

`GridItemDecoration` is a utility class used to add consistent spacing (dividers) between items in a RecyclerView configured with a grid layout.

It supports both `GridLayoutManager` and `StaggeredGridLayoutManager`, and works with both vertical and horizontal orientations.

> 📄 This documentation was assisted by ChatGPT.

## Usage

### Constructor

```kotlin
GridItemDecoration(horizontal: Float, vertical: Float)
```

- `horizontal`: horizontal spacing between grid items (in dp).
- `vertical`: vertical spacing between grid items (in dp).

### Example

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
recyclerView.layoutManager = GridLayoutManager(context, 3)
recyclerView.addItemDecoration(GridItemDecoration(8f, 12f))
```

This sets up a 3-column grid with 8dp horizontal spacing and 12dp vertical spacing between items.

## Known Issue

When updating the adapter via `DiffUtil`, or manually calling:

```kotlin
adapter.notifyItemInserted(position)
adapter.notifyItemRemoved(position)
```

... the dividers may not update properly.

### ✅ Solution

After updating the data set, also call:

```kotlin
adapter.notifyItemRangeChanged(0, itemCount)
```

This forces the RecyclerView to recalculate spacing for all items.