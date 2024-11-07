Language Versions: [Español](./README.es-ES.md) | [中文](./README.zh-CN.md)

# LinearItemDecoration

**Link to source code**: https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/LinearItemDecoration.kt

`LinearItemDecoration` adds customizable spacing and optional color dividers between items in a `LinearLayoutManager`-based RecyclerView.

It supports both vertical and horizontal orientations and allows optional color and padding configuration for dividers.

> 📄 This documentation was assisted by ChatGPT.

## Usage

### Constructor

```kotlin
LinearItemDecoration(space: Float)
```

- `space`: the spacing between items (in dp).

### Optional Configurations

```kotlin
decoration.setColor(Color.GRAY)
decoration.setPadding(16f, 16f)
```

- `setColor`: sets the divider color (ARGB).
- `setPadding`: sets start and end padding (in dp) for the divider.

### Example

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
val decoration = LinearItemDecoration(8f)
    .setColor(Color.LTGRAY)
    .setPadding(12f, 12f)

recyclerView.layoutManager = LinearLayoutManager(context)
recyclerView.addItemDecoration(decoration)
```

## Known Issue

When using `DiffUtil` or calling:

```kotlin
adapter.notifyItemInserted(position)
adapter.notifyItemRemoved(position)
```

...the dividers may not render correctly.

### ✅ Solution

After updating the data, manually trigger a partial refresh:

```kotlin
adapter.notifyItemRangeChanged(0, 2)
```