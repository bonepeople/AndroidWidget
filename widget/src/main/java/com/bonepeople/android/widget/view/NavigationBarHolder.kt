package com.bonepeople.android.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bonepeople.android.widget.util.AppSystem

/**
 * 导航栏占位控件
 * + 可以把这个控件放在界面的布局中，宽高设置为wrap_content，这样会出现一个宽高都是导航栏高度的方块，
 * + 其他的布局根据这个控件的位置就可以方便的空出导航栏的空间。使用这个控件也可以解决布局中无法引用导航栏高度的问题。
 */
class NavigationBarHolder @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val navigationBarHeight = AppSystem.getNavigationBarHeight()
        setMeasuredDimension(navigationBarHeight, navigationBarHeight)
    }
}