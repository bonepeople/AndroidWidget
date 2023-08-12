package com.bonepeople.android.widget.view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bonepeople.android.dimensionutil.DimensionUtil
import kotlin.math.roundToInt

/**
 * 网格布局RecyclerView的分割线
 *
 * 适用于GridLayoutManager和StaggeredGridLayoutManager
 */
@Suppress("UNUSED")
class GridItemDecoration(horizontal: Float, vertical: Float) : RecyclerView.ItemDecoration() {
    private var horizontalSpacing = 0
    private var verticalSpacing = 0

    init {
        horizontalSpacing = DimensionUtil.getPx(horizontal)
        verticalSpacing = DimensionUtil.getPx(vertical)
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