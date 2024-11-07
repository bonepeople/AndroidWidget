多语言版本：[English](./README.md) | [Español](./README.es-ES.md)

# LinearItemDecoration

**源代码链接**：https://github.com/bonepeople/AndroidWidget/blob/main/widget/src/main/java/com/bonepeople/android/widget/view/LinearItemDecoration.kt

`LinearItemDecoration` 是一个用于在 `LinearLayoutManager` 布局下的 RecyclerView 项目之间添加自定义间距和分割线的类。

支持横向和纵向布局，且可配置分割线颜色与边距。

> 📄 本文档由 ChatGPT 协助完成。

## 使用方法

### 构造函数

```kotlin
LinearItemDecoration(space: Float)
```

- `space`：项目之间的间距（单位：dp）

### 可选配置

```kotlin
decoration.setColor(Color.GRAY)
decoration.setPadding(16f, 16f)
```

- `setColor`：设置分割线颜色（ARGB 格式）
- `setPadding`：设置分割线的起始与结束边距（单位：dp）

### 示例

```kotlin
val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
val decoration = LinearItemDecoration(8f)
    .setColor(Color.LTGRAY)
    .setPadding(12f, 12f)

recyclerView.layoutManager = LinearLayoutManager(context)
recyclerView.addItemDecoration(decoration)
```

## 已知问题

当使用 `DiffUtil` 或手动调用如下方法时：

```kotlin
adapter.notifyItemInserted(position)
adapter.notifyItemRemoved(position)
```

分割线可能无法正确绘制。

### ✅ 解决方法

在更新数据后，手动触发部分刷新：

```kotlin
adapter.notifyItemRangeChanged(0, 2)
```