package com.bonepeople.android.widget.util

import android.app.Activity
import android.util.DisplayMetrics
import android.util.TypedValue
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.ApplicationHolder

/**
 * Android Pixel Density Conversion Utility
 *
 * Provides methods to convert between various unit types (px, dp, sp) using screen density parameters.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
object AppDensity {
    /**
     * Global screen metrics
     * Used as a fallback when no specific metrics are provided during conversion.
     */
    var defaultMetrics: DisplayMetrics? = null

    /**
     * Converts a value to pixels (px).
     * Transforms a value from the specified unit to px, defaulting to [TypedValue.COMPLEX_UNIT_DIP].
     * @param value The value to convert.
     * @param unit  The unit of the value to be converted. Based on [TypedValue] unit definitions.
     *   Defaults to [TypedValue.COMPLEX_UNIT_DIP].
     * @param metrics Optional screen metrics to use for the conversion. Defaults to global or current screen metrics.
     * @return The converted value in pixels (px).
     */
    fun getPx(value: Float, unit: Int = TypedValue.COMPLEX_UNIT_DIP, metrics: DisplayMetrics? = null): Int {
        return TypedValue.applyDimension(unit, value, getCurrentMetrics(metrics)).toInt()
    }

    /**
     * Converts a pixel (px) value to density-independent pixels (dp).
     *
     * @param px The value in pixels to convert.
     * @param metrics Optional screen metrics to use for the conversion. Defaults to global or current screen metrics.
     * @return The converted value in dp.
     */
    fun getDp(px: Int, metrics: DisplayMetrics? = null): Float {
        return px / getCurrentMetrics(metrics).density
    }

    /**
     * Converts a pixel (px) value to scale-independent pixels (sp).
     *
     * @param px The value in pixels to convert.
     * @param metrics Optional screen metrics to use for the conversion. Defaults to global or current screen metrics.
     * @return The converted value in sp.
     */
    fun getSp(px: Int, metrics: DisplayMetrics? = null): Float {
        return px / getCurrentMetrics(metrics).scaledDensity
    }

    /**
     * Retrieves the current screen metrics.
     * Determines the most applicable screen metrics based on the following priority:
     * - Provided [metrics] parameter
     * - [defaultMetrics] if set
     * - Metrics from the top [Activity] in [ActivityHolder]
     * - Metrics from the [ApplicationHolder]'s application resources
     *
     * @param metrics Optional screen metrics to use. If null, a suitable fallback is selected.
     * @return The selected [DisplayMetrics].
     */
    fun getCurrentMetrics(metrics: DisplayMetrics? = null): DisplayMetrics {
        return metrics ?: defaultMetrics ?: ActivityHolder.getTopActivity()?.resources?.displayMetrics ?: ApplicationHolder.app.resources.displayMetrics
    }
}