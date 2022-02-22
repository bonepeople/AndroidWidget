package com.bonepeople.android.widget.util

import android.view.View
import com.bonepeople.android.widget.R

fun <T : View> T.singleClick(interval: Long = 1000, action: (T) -> Unit): T = apply {
    setOnClickListener {
        val now = System.currentTimeMillis()
        val lastClickTime = getTag(R.id.lastClickTime) as? Long ?: 0
        if (now - lastClickTime > interval) {
            setTag(R.id.lastClickTime, now)
            action.invoke(this)
        }
    }
}