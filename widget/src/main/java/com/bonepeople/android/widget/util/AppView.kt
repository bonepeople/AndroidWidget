package com.bonepeople.android.widget.util

import android.view.View
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import com.bonepeople.android.widget.R

@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object AppView {
    var singleClickInterval = 800L

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

    fun <T : View> T.show(): T = apply {
        if (visibility != View.VISIBLE) visibility = View.VISIBLE
    }

    fun <T : View> T.hide(): T = apply {
        if (visibility != View.INVISIBLE) visibility = View.INVISIBLE
    }

    fun <T : View> T.gone(): T = apply {
        if (visibility != View.GONE) visibility = View.GONE
    }

    fun <T : View> T.switchShow(visible: Boolean, action: (view: T) -> Unit = {}): T = apply {
        if (visible) show() else gone()
        if (visible) action(this)
    }

    fun <T : View> T.switchVisible(visible: Boolean, action: (view: T) -> Unit = {}): T = apply {
        if (visible) show() else hide()
        if (visible) action(this)
    }

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

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> View.getExtra(key: Any, default: T): T {
        return (getTag(R.id.viewExtra) as? HashMap<Any, Any>)?.let { map ->
            map[key] as? T
        } ?: default
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> View.removeExtra(key: Any): T? {
        return (getTag(R.id.viewExtra) as? HashMap<Any, Any>)?.let { map ->
            map.remove(key) as? T
        }
    }

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

    class MeasureParameter {
        @Transient
        var widthMode: Int = 0
        var widthModeName: String = ""
        var widthSize: Int = 0

        @Transient
        var heightMode: Int = 0
        var heightModeName: String = ""
        var heightSize: Int = 0

        var maxWidth: Int = 0
        var maxHeight: Int = 0

        var targetWidth: Int? = null
        var targetHeight: Int? = null
    }
}