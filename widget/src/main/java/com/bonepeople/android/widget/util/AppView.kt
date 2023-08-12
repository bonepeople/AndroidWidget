package com.bonepeople.android.widget.util

import android.view.View
import com.bonepeople.android.widget.R

/**
 * View工具类
 */
@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object AppView {
    /**
     * 单击防抖函数的默认时间间隔
     */
    var singleClickInterval = 800L

    /**
     * 单击防抖函数，确保固定时间内仅响应首次点击事件，每个控件独立判断
     * @param interval 两次点击的间隔
     */
    fun <T : View> T.singleClick(interval: Long = singleClickInterval, action: (T) -> Unit): T = apply {
        setOnClickListener {
            val now = System.currentTimeMillis()
            val lastClickTime = getTag(R.id.lastClickTime) as? Long ?: 0
            if (now - lastClickTime > interval) {
                setTag(R.id.lastClickTime, now)
                action.invoke(this)
            }
        }
    }

    /**
     * 设置[View]为[View.VISIBLE]
     */
    fun <T : View> T.show(): T = apply {
        if (visibility != View.VISIBLE) visibility = View.VISIBLE
    }

    /**
     * 设置[View]为[View.INVISIBLE]
     */
    fun <T : View> T.hide(): T = apply {
        if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
    }

    /**
     * 设置[View]为[View.GONE]
     */
    fun <T : View> T.gone(): T = apply {
        if (visibility != View.GONE) visibility = View.GONE
    }

    /**
     * 设置[View]的可见性
     * + View的可见性会根据[visible]的值在[View.VISIBLE]或[View.GONE]中切换
     * @param visible 是否可见
     */
    fun <T : View> T.switchShow(visible: Boolean): T = apply {
        if (visible) show() else gone()
    }

    /**
     * 设置[View]的可见性
     * + View的可见性会根据[visible]的值在[View.VISIBLE]或[View.INVISIBLE]中切换
     * @param visible 是否可见
     */
    fun <T : View> T.switchVisible(visible: Boolean): T = apply {
        if (visible) show() else hide()
    }
}