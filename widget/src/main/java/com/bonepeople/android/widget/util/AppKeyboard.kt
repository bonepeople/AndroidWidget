package com.bonepeople.android.widget.util

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.postDelayed
import com.bonepeople.android.widget.ActivityHolder

@Suppress("UNUSED")
object AppKeyboard {
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

    fun showKeyboard(editText: EditText) {
        editText.postDelayed(200) {
            editText.requestFocus()
            val inputMethodManager = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }
    }

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