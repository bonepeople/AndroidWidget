package com.bonepeople.android.widget.util

import android.util.DisplayMetrics
import android.util.TypedValue
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.ApplicationHolder

/**
 * Android像素密度转换工具类
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
object AppDensity {
    /**
     * 全局的屏幕参数
     * + 在转换时未提供屏幕参数的情况下使用
     */
    var defaultMetrics: DisplayMetrics? = null

    /**
     * 获取px单位数值
     * + 将指定单位的数值转换为px单位的数值
     * @param value 被转换数值
     * @param unit  转换单位，参照[TypedValue]的单位定义，默认为[TypedValue.COMPLEX_UNIT_DIP]
     * @return 转换后的数值，单位为px
     */
    fun getPx(value: Float, unit: Int = TypedValue.COMPLEX_UNIT_DIP, metrics: DisplayMetrics? = null): Int {
        return TypedValue.applyDimension(unit, value, getCurrentMetrics(metrics)).toInt()
    }

    /**
     * 获取dp单位数值
     * + 将px单位的数值转换为dp单位的数值
     * @param px 被转换数值，单位为px
     * @return 转换后的数值，单位为dp
     */
    fun getDp(px: Int, metrics: DisplayMetrics? = null): Float {
        return px / getCurrentMetrics(metrics).density
    }

    /**
     * 获取sp单位数值
     * + 将px单位的数值转换为sp单位的数值
     * @param px 被转换数值，单位为px
     * @return 转换后的数值，单位为sp
     */
    fun getSp(px: Int, metrics: DisplayMetrics? = null): Float {
        return px / getCurrentMetrics(metrics).scaledDensity
    }

    /**
     * 获取当前屏幕参数
     * + 根据优先级返回当前可用的屏幕参数
     * + 优先级：传入的参数 > 全局参数 > 当前Activity的屏幕参数 > Application的屏幕参数
     */
    fun getCurrentMetrics(metrics: DisplayMetrics? = null): DisplayMetrics {
        return metrics ?: defaultMetrics ?: ActivityHolder.getTopActivity()?.resources?.displayMetrics ?: ApplicationHolder.app.resources.displayMetrics
    }
}