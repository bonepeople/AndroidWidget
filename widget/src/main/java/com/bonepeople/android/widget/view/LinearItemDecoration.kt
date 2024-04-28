package com.bonepeople.android.widget.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bonepeople.android.widget.util.AppDensity
import kotlin.math.roundToInt

/**
 * Divider for a linear-layout RecyclerView
 *
 * + Suitable for [LinearLayoutManager].
 *
 * ## Known Issue:
 * When updating the list using `DiffUtil` or manually calling `notifyItemInserted`/`notifyItemRemoved`,
 * the dividers might not update correctly.
 *
 * - **Reason**: Adding or removing items doesn’t refresh the dimensions of dividers for other elements in the list.
 * - **Solution**: After updating the list, manually call `notifyItemRangeChanged(0, 2)` to refresh the first two items.
 *   If multiple items are added, adjust the range to include all affected items.
 */
@Suppress("Unused")
class LinearItemDecoration(space: Float) : RecyclerView.ItemDecoration() {
    private var spacing = 0
    private var startPadding = 0
    private var endPadding = 0
    private val paint: Paint by lazy { Paint().apply { color = 0x00FFFFFF } }
    private val bounds: Rect = Rect()

    init {
        spacing = AppDensity.getPx(space)
    }

    /**
     * Sets the color of the divider.
     * @param color The color value in ARGB format.
     * @return The current instance for chaining calls.
     */
    fun setColor(@ColorInt color: Int) = apply {
        paint.color = color
    }

    /**
     * Sets the padding for the divider.
     * @param start Padding at the start of the divider (in dp).
     * @param end Padding at the end of the divider (in dp).
     * @return The current instance for chaining calls.
     */
    fun setPadding(start: Float, end: Float) = apply {
        startPadding = AppDensity.getPx(start)
        endPadding = AppDensity.getPx(end)
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val layoutManager = parent.layoutManager
        if (layoutManager is LinearLayoutManager && parent.getChildLayoutPosition(view) != 0) {
            when (layoutManager.orientation) {
                RecyclerView.HORIZONTAL -> {
                    if (parent.layoutDirection == View.LAYOUT_DIRECTION_LTR) { // Left-to-right layout
                        outRect.set(spacing, 0, 0, 0)
                    } else { // Right-to-left layout
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
            if (parent.layoutDirection == View.LAYOUT_DIRECTION_LTR) { // Left-to-right layout
                left = parent.paddingStart
                right = parent.width - parent.paddingEnd
            } else { // Right-to-left layout
                left = parent.paddingEnd
                right = parent.width - parent.paddingStart
            }
            canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }

        if (parent.layoutDirection == View.LAYOUT_DIRECTION_LTR) { // Left-to-right layout
            left += startPadding
            right -= endPadding
        } else { // Right-to-left layout
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
            if (parent.layoutDirection == View.LAYOUT_DIRECTION_LTR) { // Left-to-right layout
                canvas.clipRect(parent.paddingStart, top, parent.width - parent.paddingEnd, bottom)
            } else { // Right-to-left layout
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