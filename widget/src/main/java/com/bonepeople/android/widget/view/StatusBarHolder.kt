package com.bonepeople.android.widget.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.bonepeople.android.widget.util.AppSystem

/**
 * 状态栏占位控件
 * + 可以把这个控件放在界面的布局中，宽高设置为wrap_content，这样会出现一个宽高都是状态栏高度的方块，
 * + 其他的布局根据这个控件的位置就可以方便的空出状态栏的空间。使用这个控件也可以解决布局中无法引用状态栏高度的问题。
 */
class StatusBarHolder @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val statusBarHeight = AppSystem.getStatusBarHeight()
        setMeasuredDimension(statusBarHeight, statusBarHeight)
    }
}