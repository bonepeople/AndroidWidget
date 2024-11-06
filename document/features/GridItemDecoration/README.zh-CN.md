多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# GridItemDecoration

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/GridItemDecoration.kt

`GridItemDecoration` 是一个用于为 RecyclerView 网格布局添加间距（分割线）的工具类。

它支持 `GridLayoutManager` 和 `StaggeredGridLayoutManager`，并兼容横向和纵向方向的布局。

> 📄 本文档由 ChatGPT 协助完成。

## 使用方法

### 构造方法

```kotlin
GridItemDecoration(horizontal: Float, vertical: Float)
```

- `horizontal`：网格项之间的水平间距（单位：dp）
- `vertical`：网格项之间的垂直间距（单位：dp）

### 示例代码

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
recyclerView.layoutManager = GridLayoutManager(context, 3)
recyclerView.addItemDecoration(GridItemDecoration(8f, 12f))
```

上述代码会设置一个三列的网格，每个项之间的水平间距为 8dp，垂直间距为 12dp。

## 已知问题

当通过 `DiffUtil` 更新数据，或手动调用如下方法时：

```kotlin
adapter.notifyItemInserted(position)
adapter.notifyItemRemoved(position)
```

分割线可能不会正确更新。

### ✅ 解决方法

在更新数据后，再调用以下代码以刷新整个列表：

```kotlin
adapter.notifyItemRangeChanged(0, itemCount)
```

这会强制 RecyclerView 重新计算所有项的间距。