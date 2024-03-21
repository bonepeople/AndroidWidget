package com.bonepeople.android.widget.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bonepeople.android.widget.util.AppDensity
import kotlin.math.roundToInt

/**
 * Divider for a grid-layout RecyclerView
 *
 * + Suitable for [GridLayoutManager] and [StaggeredGridLayoutManager].
 *
 * ## Known Issue:
 * When updating the list using `DiffUtil` or manually calling `notifyItemInserted`/`notifyItemRemoved`,
 * the dividers might not update correctly.
 *
 * - **Reason**: Adding or removing items doesn’t refresh the dimensions of dividers for other elements in the list.
 * - **Solution**: After updating the list, call `notifyItemRangeChanged(0, list.size)` to refresh the entire list,
 * ensuring the dividers are updated correctly.
 */
@Suppress("unused")
class GridItemDecoration(horizontal: Float, vertical: Float) : RecyclerView.ItemDecoration() {
    private var horizontalSpacing = 0
    private var verticalSpacing = 0

    init {
        horizontalSpacing = AppDensity.getPx(horizontal)
        verticalSpacing = AppDensity.getPx(vertical)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        var (index, left, top, right, bottom) = listOf(0, 0, 0, 0, 0)
        var spanCount = 0f
        var orientation = RecyclerView.VERTICAL

        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            index = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
            spanCount = layoutManager.spanCount.toFloat()
            orientation = layoutManager.orientation
        }
        if (layoutManager is StaggeredGridLayoutManager) {
            index = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex
            spanCount = layoutManager.spanCount.toFloat()
            orientation = layoutManager.orientation
        }

        when (orientation) {
            RecyclerView.HORIZONTAL -> {
                if (parent.getChildAdapterPosition(view) >= spanCount) {
                    left = horizontalSpacing
                }
                top = (index / spanCount * verticalSpacing).roundToInt()
                bottom = ((spanCount - 1 - index) / spanCount * verticalSpacing).roundToInt()
            }

            RecyclerView.VERTICAL -> {
                left = (index / spanCount * horizontalSpacing).roundToInt()
                right = ((spanCount - 1 - index) / spanCount * horizontalSpacing).roundToInt()
                if (parent.getChildAdapterPosition(view) >= spanCount) {
                    top = verticalSpacing
                }
            }
        }
        outRect.set(left, top, right, bottom)
    }
}