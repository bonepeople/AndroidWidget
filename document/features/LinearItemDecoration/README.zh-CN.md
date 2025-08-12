多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# LinearItemDecoration

## 简介

`LinearItemDecoration` 为 `LinearLayoutManager` 布局的 `RecyclerView` 添加可定制间距和可选颜色分隔线，支持垂直和水平方向。

## 功能

- 可配置项间距（单位 dp）
- 可选的分隔线颜色和首尾内边距

## 使用方式

构造函数：

```kotlin
LinearItemDecoration(space: Float)
```

- `space`：项之间的间距（dp）

可选配置：

```kotlin
decoration.setColor(Color.GRAY)
decoration.setPadding(16f, 16f)
```

- `setColor`：分隔线颜色（ARGB）
- `setPadding`：分隔线首尾内边距（dp）

示例：

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
val decoration = LinearItemDecoration(8f)
    .setColor(Color.LTGRAY)
    .setPadding(12f, 12f)

recyclerView.layoutManager = LinearLayoutManager(context)
recyclerView.addItemDecoration(decoration)
```

## 注意事项

使用 `DiffUtil` 或调用 `notifyItemInserted` / `notifyItemRemoved` 时，分隔线可能无法正确渲染。

更新数据后，手动触发局部刷新：

```kotlin
adapter.notifyItemRangeChanged(0, 2)
```

## 源码链接

[LinearItemDecoration.kt](https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/LinearItemDecoration.kt)