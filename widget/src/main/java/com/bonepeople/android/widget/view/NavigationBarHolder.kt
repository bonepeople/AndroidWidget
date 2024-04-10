package com.bonepeople.android.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bonepeople.android.widget.util.AppSystem

class NavigationBarHolder @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val navigationBarHeight = AppSystem.getNavigationBarHeight()
        setMeasuredDimension(navigationBarHeight, navigationBarHeight)
    }
}