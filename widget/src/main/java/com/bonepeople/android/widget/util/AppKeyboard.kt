package com.bonepeople.android.widget.util

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.postDelayed
import com.bonepeople.android.widget.ActivityHolder

/**
 * 软键盘工具类
 */
@Suppress("UNUSED")
object AppKeyboard {
    /**
     * 根据触摸事件判断是否需要隐藏软键盘
     * @param focusedView 当前获取焦点的控件，可以通过[android.view.Window.getCurrentFocus]获取
     * @param motionEvent 当前手指的触摸事件
     */
    fun needHideKeyboard(focusedView: View?, motionEvent: MotionEvent): Boolean {
        if (focusedView is EditText) {
            val location = intArrayOf(0, 0)
            focusedView.getLocationInWindow(location)
            val left = location[0]
            val top = location[1]
            val right = left + focusedView.width
            val bottom = top + focusedView.height
            return !(motionEvent.x > left && motionEvent.x < right && motionEvent.y > top && motionEvent.y < bottom)
        }
        return false
    }

    /**
     * 为一个输入框显示软键盘
     * @param editText 需要输入内容的输入框
     */
    @Deprecated("Use showKeyboard(editText: EditText) instead", ReplaceWith("AppKeyboard.showKeyboard(editText)"))
    fun showKeyboard(context: Context, editText: EditText) = showKeyboard(editText)

    /**
     * 为一个输入框显示软键盘
     * @param editText 需要输入内容的输入框
     */
    fun showKeyboard(editText: EditText) {
        editText.postDelayed(200) {
            editText.requestFocus()
            val inputMethodManager = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    /**
     * 隐藏软键盘
     * @param focusedView 当前获取焦点的控件
     */
    @Deprecated("Use hideKeyboard() instead", ReplaceWith("AppKeyboard.hideKeyboard()"))
    fun hideKeyboard(context: Context, focusedView: View?) = hideKeyboard()

    /**
     * 隐藏软键盘
     */
    fun hideKeyboard() {
        val focusedView = ActivityHolder.getTopActivity()?.window?.currentFocus
        focusedView?.run {
            clearFocus()
            val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            windowToken?.let { token ->
                inputMethodManager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
            }
        }
    }
}