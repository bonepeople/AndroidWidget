package com.bonepeople.android.widget.util

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.TypedValue
import androidx.annotation.ColorInt

/**
 * 富文本拼接工具类
 */
@Suppress("UNUSED")
open class AppSpan : SpannableStringBuilder() {
    /**
     * 添加一段文本，可以附加多个Span效果
     * + BackgroundColorSpan : 文本背景色
     * + ForegroundColorSpan : 文本颜色
     * + MaskFilterSpan : 修饰效果，如模糊(BlurMaskFilter)浮雕
     * + RasterizerSpan : 光栅效果
     * + StrikethroughSpan : 删除线
     * + SuggestionSpan : 相当于占位符
     * + UnderlineSpan : 下划线
     * + AbsoluteSizeSpan : 文本字体（绝对大小）
     * + DynamicDrawableSpan : 设置图片，基于文本基线或底部对齐。
     * + ImageSpan : 图片
     * + RelativeSizeSpan : 相对大小（文本字体）
     * + ScaleXSpan : 基于x轴缩放
     * + StyleSpan : 字体样式：粗体、斜体等
     * + SubscriptSpan : 下标（数学公式会用到）
     * + SuperscriptSpan : 上标（数学公式会用到）
     * + TextAppearanceSpan : 文本外貌（包括字体、大小、样式和颜色）
     * + TypefaceSpan : 文本字体
     * + URLSpan : 文本超链接
     * + ClickableSpan : 点击事件
     */
    fun text(content: CharSequence, vararg effects: Any) = apply {
        val span = SpannableString(content)
        effects.forEach {
            span.setSpan(it, 0, content.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        append(span)
    }

    /**
     * 添加一段文本并设置这段文本的文字颜色
     */
    fun textColor(content: CharSequence, @ColorInt color: Int) = apply {
        append(content, ForegroundColorSpan(color), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    /**
     * 添加一段文本并设置这段文本的文字大小
     */
    fun textSize(content: CharSequence, size: Float) = apply {
        append(content, AbsoluteSizeSpan(AppDensity.getPx(size, TypedValue.COMPLEX_UNIT_SP)), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    /**
     * 添加一段文本并设置这段文本的文字缩放比例
     */
    fun textScale(content: CharSequence, scale: Float) = apply {
        append(content, RelativeSizeSpan(scale), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    /**
     * 添加一段文本并设置这段文本的背景颜色
     */
    fun backgroundColor(content: CharSequence, @ColorInt color: Int) = apply {
        append(content, BackgroundColorSpan(color), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    companion object {
        fun text(content: CharSequence, vararg effects: Any) = AppSpan().text(content, effects)
        fun textColor(content: CharSequence, @ColorInt color: Int) = AppSpan().textColor(content, color)
        fun textSize(content: CharSequence, size: Float) = AppSpan().textSize(content, size)
        fun textScale(content: CharSequence, scale: Float) = AppSpan().textScale(content, scale)
        fun backgroundColor(content: CharSequence, @ColorInt color: Int) = AppSpan().backgroundColor(content, color)
    }
}