package com.bonepeople.android.widget.util

import android.view.View
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.bonepeople.android.widget.R

/**
 * Utility class for managing View operations
 *
 * [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/AppView)
 */
@Suppress("Unused", "MemberVisibilityCanBePrivate")
object AppView {
    /**
     * Default interval for debounce function for single clicks
     */
    var singleClickInterval = 800L

    /**
     * Ensures that a click action is triggered only once within the specified interval, unique for each view.
     * @param interval Interval between consecutive clicks
     */
    fun <T : View> T.singleClick(interval: Long = singleClickInterval, action: (T) -> Unit): T = apply {
        setOnClickListener { view: View ->
            val now = System.currentTimeMillis()
            val lastClickTime: Long = view.getExtra("com.bonepeople.android.widget.keys.AppView.lastClickTime", 0)
            if (now - lastClickTime > interval) {
                view.putExtra("com.bonepeople.android.widget.keys.AppView.lastClickTime", now)
                action.invoke(this)
            }
        }
    }

    /**
     * Sets the [View] to [View.VISIBLE]
     */
    fun <T : View> T.show(): T = apply {
        if (visibility != View.VISIBLE) visibility = View.VISIBLE
    }

    /**
     * Sets the [View] to [View.INVISIBLE]
     */
    fun <T : View> T.hide(): T = apply {
        if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
    }

    /**
     * Sets the [View] to [View.GONE]
     */
    fun <T : View> T.gone(): T = apply {
        if (visibility != View.GONE) visibility = View.GONE
    }

    /**
     * Toggles the visibility of the [View] between [View.VISIBLE] and [View.GONE].
     * @param visible Whether the view should be visible
     * @param action Action to perform if the view becomes visible
     */
    fun <T : View> T.switchShow(visible: Boolean, action: (view: T) -> Unit = {}): T = apply {
        if (visible) show() else gone()
        if (visible) action(this)
    }

    /**
     * Toggles the visibility of the [View] between [View.VISIBLE] and [View.INVISIBLE].
     * @param visible Whether the view should be visible
     * @param action Action to perform if the view becomes visible
     */
    fun <T : View> T.switchVisible(visible: Boolean, action: (view: T) -> Unit = {}): T = apply {
        if (visible) show() else hide()
        if (visible) action(this)
    }

    /**
     * Stores a key-value pair in the View.
     * + Data is stored in a HashMap.
     * + Returns the stored value.
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
     * Retrieves a key-value pair stored in the View.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> View.getExtra(key: Any, default: T): T {
        return (getTag(R.id.viewExtra) as? HashMap<Any, Any>)?.let { map ->
            map[key] as? T
        } ?: default
    }

    /**
     * Removes a key-value pair from the View and returns the value. Returns null if the key doesn't exist.
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> View.removeExtra(key: Any): T? {
        return (getTag(R.id.viewExtra) as? HashMap<Any, Any>)?.let { map ->
            map.remove(key) as? T
        }
    }

    /**
     * Resolves measurement parameters to calculate the maximum space a View can occupy.
     * + Typically called in the onMeasure function for custom Views.
     * @param view The target View
     * @param widthMeasureSpec Width measurement spec from the parent
     * @param heightMeasureSpec Height measurement spec from the parent
     * @return A parameter object containing measurement data
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
     * Measurement parameters
     * + Contains data parsed from MeasureSpec for calculating the maximum dimensions of a View.
     */
    class MeasureParameter {
        // Parameters derived from widthMeasureSpec
        @Transient
        var widthMode: Int = 0
        var widthModeName: String = ""
        var widthSize: Int = 0

        // Parameters derived from heightMeasureSpec
        @Transient
        var heightMode: Int = 0
        var heightModeName: String = ""
        var heightSize: Int = 0

        // Maximum width and height the View can occupy in the parent layout
        var maxWidth: Int = 0
        var maxHeight: Int = 0

        // Width and height explicitly specified by the parent layout, null if not specified
        var targetWidth: Int? = null
        var targetHeight: Int? = null
    }
}