package com.bonepeople.android.widget.util

import android.view.View
import com.bonepeople.android.widget.R

var singleClickInterval = 800L

fun <T : View> T.singleClick(interval: Long = singleClickInterval, action: (T) -> Unit): T = apply {
    setOnClickListener {
        val now = System.currentTimeMillis()
        val lastClickTime = getTag(R.id.lastClickTime) as? Long ?: 0
        if (now - lastClickTime > interval) {
            setTag(R.id.lastClickTime, now)
            action.invoke(this)
        }
    }
}