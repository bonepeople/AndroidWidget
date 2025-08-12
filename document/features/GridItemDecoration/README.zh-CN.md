多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# GridItemDecoration

## 简介

`GridItemDecoration` 为网格布局的 `RecyclerView` 添加统一间距。支持 `GridLayoutManager` 和 `StaggeredGridLayoutManager`，兼容垂直和水平方向。

## 功能

- 可配置水平和垂直间距（单位 dp）
- 适用于网格和交错网格布局

## 使用方式

构造函数：

```kotlin
GridItemDecoration(horizontal: Float, vertical: Float)
```

- `horizontal`：网格项之间的水平间距（dp）
- `vertical`：网格项之间的垂直间距（dp）

示例：

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
recyclerView.layoutManager = GridLayoutManager(context, 3)
recyclerView.addItemDecoration(GridItemDecoration(8f, 12f))
```

上述配置为 3 列网格，水平间距 8dp，垂直间距 12dp。

## 注意事项

通过 `DiffUtil` 更新适配器，或调用 `notifyItemInserted` / `notifyItemRemoved` 时，分隔线可能不会正确更新。

更新数据集后，还需调用：

```kotlin
adapter.notifyItemRangeChanged(0, itemCount)
```

以强制 RecyclerView 重新计算所有项的间距。

## 源码链接

[GridItemDecoration.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/GridItemDecoration.kt)