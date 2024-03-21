package com.bonepeople.android.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bonepeople.android.widget.util.AppSystem

/**
 * Status Bar Placeholder
 *
 * + This widget can be added to a layout with its width and height set to `wrap_content`.
 * + It creates a square block with both width and height equal to the height of the status bar.
 * + By positioning this widget in the layout, it helps reserve space for the status bar conveniently.
 * + Additionally, this widget resolves issues where the status bar height cannot be directly referenced in layouts.
 */
class StatusBarHolder @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val statusBarHeight = AppSystem.getStatusBarHeight()
        // Sets the dimensions of this view to match the status bar's height
        setMeasuredDimension(statusBarHeight, statusBarHeight)
    }
}