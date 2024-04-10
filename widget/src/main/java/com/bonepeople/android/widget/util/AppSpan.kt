package com.bonepeople.android.widget.util

import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.AbsoluteSizeSpan
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.util.TypedValue
import androidx.annotation.ColorInt

@Suppress("UNUSED")
open class AppSpan : SpannableStringBuilder() {
    fun text(content: CharSequence, vararg effects: Any) = apply {
        val span = SpannableString(content)
        effects.forEach {
            span.setSpan(it, 0, content.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        append(span)
    }

    fun textColor(content: CharSequence, @ColorInt color: Int) = apply {
        append(content, ForegroundColorSpan(color), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun textSize(content: CharSequence, size: Float) = apply {
        append(content, AbsoluteSizeSpan(AppDensity.getPx(size, TypedValue.COMPLEX_UNIT_SP)), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    fun textScale(content: CharSequence, scale: Float) = apply {
        append(content, RelativeSizeSpan(scale), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

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