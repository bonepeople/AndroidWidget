package com.bonepeople.android.widget.util

import android.view.View
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
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
     * @param action 可见时需要执行的操作
     */
    fun <T : View> T.switchShow(visible: Boolean, action: (view: T) -> Unit = {}): T = apply {
        if (visible) show() else gone()
        if (visible) action(this)
    }

    /**
     * 设置[View]的可见性
     * + View的可见性会根据[visible]的值在[View.VISIBLE]或[View.INVISIBLE]中切换
     * @param visible 是否可见
     * @param action 可见时需要执行的操作
     */
    fun <T : View> T.switchVisible(visible: Boolean, action: (view: T) -> Unit = {}): T = apply {
        if (visible) show() else hide()
        if (visible) action(this)
    }

    /**
     * 将指定的Key-Value数据存储在View中
     * + 数据会被存入HashMap中
     * + 存储过程完成后会将存储的数据返回
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> View.putExtra(key: Any, value: T): T {
        (getTag(R.id.viewExtra) as? HashMap<Any, Any>)?.let { map ->
            map[key] = value
        } ?: run {
            val map = HashMap<Any, Any>()
            map[key] = value
            setTag(R.id.viewExtra, map)
        }
        return value
    }

    /**
     * 获取View中存储的Key-Value
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> View.getExtra(key: Any, default: T): T {
        return (getTag(R.id.viewExtra) as? HashMap<Any, Any>)?.let { map ->
            map[key] as? T
        } ?: default
    }

    /**
     * 移除View中存储的Key-Value并将数据返回，没有相应字段的情况下会返回空指针
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> View.removeExtra(key: Any): T? {
        return (getTag(R.id.viewExtra) as? HashMap<Any, Any>)?.let { map ->
            map.remove(key) as? T
        }
    }

    /**
     * 解析测算参数，计算指定View可以占用的最大空间
     * + 通常在onMeasure函数中调用，用于解析MeasureSpec
     * @param view 需要计算的View
     * @param widthMeasureSpec 父控件的宽度测算规格
     * @param heightMeasureSpec 父控件的高度测算规格
     * @return 一个包含测量参数的对象，用于测量自定义View
     */
    fun resolveMeasureParameter(view: View, widthMeasureSpec: Int, heightMeasureSpec: Int): MeasureParameter = MeasureParameter().apply {
        widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        when (widthMode) {
            View.MeasureSpec.EXACTLY -> {
                widthModeName = "EXACTLY"
                maxWidth = widthSize
                targetWidth = widthSize
            }

            View.MeasureSpec.AT_MOST -> {
                widthModeName = "AT_MOST"
                maxWidth = widthSize - view.marginStart - view.marginEnd
            }

            View.MeasureSpec.UNSPECIFIED -> {
                widthModeName = "UNSPECIFIED"
                maxWidth = Int.MAX_VALUE
            }
        }
        when (heightMode) {
            View.MeasureSpec.EXACTLY -> {
                heightModeName = "EXACTLY"
                maxHeight = heightSize
                targetHeight = heightSize
            }

            View.MeasureSpec.AT_MOST -> {
                heightModeName = "AT_MOST"
                maxHeight = heightSize - view.marginTop - view.marginBottom
            }

            View.MeasureSpec.UNSPECIFIED -> {
                heightModeName = "UNSPECIFIED"
                maxHeight = Int.MAX_VALUE
            }
        }
    }

    /**
     * 测算参数
     * + 包含解析得到的测算数据，可用于计算View的最大尺寸
     */
    class MeasureParameter {
        // 通过解析widthMeasureSpec得到的参数
        @Transient
        var widthMode: Int = 0
        var widthModeName: String = ""
        var widthSize: Int = 0

        // 通过解析heightMeasureSpec得到的参数
        @Transient
        var heightMode: Int = 0
        var heightModeName: String = ""
        var heightSize: Int = 0

        // 测算View在父布局中可占用的最大宽高
        var maxWidth: Int = 0
        var maxHeight: Int = 0

        // 父布局指定的宽高，若未指定则为null
        var targetWidth: Int? = null
        var targetHeight: Int? = null
    }
}