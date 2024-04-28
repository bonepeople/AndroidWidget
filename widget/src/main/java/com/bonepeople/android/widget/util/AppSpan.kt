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
 * Utility class for building rich text with spans
 */
@Suppress("Unused")
open class AppSpan : SpannableStringBuilder() {
    /**
     * Adds a text segment with multiple span effects.
     * Supported spans include:
     * + BackgroundColorSpan: Text background color
     * + ForegroundColorSpan: Text color
     * + MaskFilterSpan: Special effects like blur (BlurMaskFilter) or emboss
     * + RasterizerSpan: Raster effects
     * + StrikethroughSpan: Strikethrough
     * + SuggestionSpan: Placeholder-like suggestions
     * + UnderlineSpan: Underline
     * + AbsoluteSizeSpan: Absolute text size
     * + DynamicDrawableSpan: Drawable aligned with text baseline or bottom
     * + ImageSpan: Embedded image
     * + RelativeSizeSpan: Relative text size
     * + ScaleXSpan: Horizontal scale
     * + StyleSpan: Text style (e.g., bold, italic)
     * + SubscriptSpan: Subscript (useful for math formulas)
     * + SuperscriptSpan: Superscript (useful for math formulas)
     * + TextAppearanceSpan: Text appearance (font, size, style, color)
     * + TypefaceSpan: Typeface
     * + URLSpan: Hyperlink
     * + ClickableSpan: Clickable event
     */
    fun text(content: CharSequence, vararg effects: Any) = apply {
        val span = SpannableString(content)
        effects.forEach {
            span.setSpan(it, 0, content.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        append(span)
    }

    /**
     * Adds a text segment and sets its text color.
     */
    fun textColor(content: CharSequence, @ColorInt color: Int) = apply {
        append(content, ForegroundColorSpan(color), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    /**
     * Adds a text segment and sets its text size.
     */
    fun textSize(content: CharSequence, size: Float) = apply {
        append(content, AbsoluteSizeSpan(AppDensity.getPx(size, TypedValue.COMPLEX_UNIT_SP)), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    /**
     * Adds a text segment and sets its text scale ratio.
     */
    fun textScale(content: CharSequence, scale: Float) = apply {
        append(content, RelativeSizeSpan(scale), SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)
    }

    /**
     * Adds a text segment and sets its background color.
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