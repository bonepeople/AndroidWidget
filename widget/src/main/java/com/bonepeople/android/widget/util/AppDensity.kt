package com.bonepeople.android.widget.util

import android.util.DisplayMetrics
import android.util.TypedValue
import com.bonepeople.android.widget.ActivityHolder
import com.bonepeople.android.widget.ApplicationHolder

@Suppress("unused", "MemberVisibilityCanBePrivate")
object AppDensity {
    var defaultMetrics: DisplayMetrics? = null

    fun getPx(value: Float, unit: Int = TypedValue.COMPLEX_UNIT_DIP, metrics: DisplayMetrics? = null): Int {
        return TypedValue.applyDimension(unit, value, getCurrentMetrics(metrics)).toInt()
    }

    fun getDp(px: Int, metrics: DisplayMetrics? = null): Float {
        return px / getCurrentMetrics(metrics).density
    }

    fun getSp(px: Int, metrics: DisplayMetrics? = null): Float {
        return px / getCurrentMetrics(metrics).scaledDensity
    }

    fun getCurrentMetrics(metrics: DisplayMetrics? = null): DisplayMetrics {
        return metrics ?: defaultMetrics ?: ActivityHolder.getTopActivity()?.resources?.displayMetrics ?: ApplicationHolder.app.resources.displayMetrics
    }
}