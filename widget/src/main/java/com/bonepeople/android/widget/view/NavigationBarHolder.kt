package com.bonepeople.android.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bonepeople.android.widget.util.AppSystem

/**
 * Navigation Bar Placeholder
 *
 * + This widget can be added to a layout with its width and height set to `wrap_content`.
 * + It creates a square block with both width and height equal to the height of the navigation bar.
 * + By positioning this widget in the layout, it helps reserve space for the navigation bar conveniently.
 * + Additionally, this widget resolves issues where the navigation bar height cannot be directly referenced in layouts.
 */
class NavigationBarHolder @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val navigationBarHeight = AppSystem.getNavigationBarHeight()
        // Sets the dimensions of this view to match the navigation bar's height
        setMeasuredDimension(navigationBarHeight, navigationBarHeight)
    }
}