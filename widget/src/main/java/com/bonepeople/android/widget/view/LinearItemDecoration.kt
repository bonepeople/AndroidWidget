package com.bonepeople.android.widget.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonepeople.android.dimensionutil.DimensionUtil
import kotlin.math.roundToInt

/**
 * 线性布局RecyclerView的分割线
 *
 * 适用于LinearLayoutManager
 */
class LinearItemDecoration(space: Float) : RecyclerView.ItemDecoration() {
    private var spacing = 0
    private var startPadding = 0
    private var endPadding = 0
    private val paint: Paint by lazy { Paint().apply { color = 0x00FFFFFF } }
    private val bounds: Rect = Rect()

    init {
        spacing = DimensionUtil.getPx(space)
    }

    fun setColor(@ColorInt color: Int) = apply {
        paint.color = color
    }

    fun setPadding(start: Float, end: Float) = apply {
        startPadding = DimensionUtil.getPx(start)
        endPadding = DimensionUtil.getPx(end)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager && parent.getChildLayoutPosition(view) != 0) {
            when (layoutManager.orientation) {
                RecyclerView.HORIZONTAL -> {
                    if (parent.layoutDirection == View.LAYOUT_DIRECTION_LTR) { //从左至右布局
                        outRect.set(spacing, 0, 0, 0)
                    } else { //从右至左布局
                        outRect.set(0, 0, spacing, 0)
                    }
                }
                RecyclerView.VERTICAL -> {
                    outRect.set(0, spacing, 0, 0)
                }
            }
        }
    }

    override fun onDraw(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (paint.color.toUInt() <= 0x00FFFFFF.toUInt()) return
        val layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager) {
            when (layoutManager.orientation) {
                RecyclerView.HORIZONTAL -> {
                    drawHorizontal(canvas, parent)
                }
                RecyclerView.VERTICAL -> {
                    drawVertical(canvas, parent)
                }
            }
        }
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        var left: Int
        var right: Int

        if (parent.clipToPadding) {
            if (parent.layoutDirection == View.LAYOUT_DIRECTION_LTR) { //从左至右布局
                left = parent.paddingStart
                right = parent.width - parent.paddingEnd
            } else {
                left = parent.paddingEnd
                right = parent.width - parent.paddingStart
            }
            canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        if (parent.layoutDirection == View.LAYOUT_DIRECTION_LTR) { //从左至右布局
            left += startPadding
            right -= endPadding
        } else {
            left += endPadding
            right - +startPadding
        }
        if (right <= left) return

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (parent.getChildLayoutPosition(child) == 0) continue
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val top = bounds.top + child.translationY.roundToInt()
            val bottom = top + spacing
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
        canvas.restore()
    }

    private fun drawHorizontal(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        var top: Int
        var bottom: Int

        if (parent.clipToPadding) {
            top = parent.paddingTop
            bottom = parent.height - parent.paddingBottom
            if (parent.layoutDirection == View.LAYOUT_DIRECTION_LTR) {
                canvas.clipRect(parent.paddingStart, top, parent.width - parent.paddingEnd, bottom)
            } else {
                canvas.clipRect(parent.paddingEnd, top, parent.width - parent.paddingStart, bottom)
            }
        } else {
            top = 0
            bottom = parent.height
        }
        top += startPadding
        bottom -= endPadding
        if (bottom <= top) return

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (parent.getChildLayoutPosition(child) == 0) continue
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val left = bounds.left + child.translationX.roundToInt()
            val right = left + spacing
            canvas.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), paint)
        }
        canvas.restore()
    }
}