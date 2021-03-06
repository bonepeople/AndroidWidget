package com.bonepeople.android.widget.util

import android.view.View
import com.bonepeople.android.widget.R

/**
 * 单击防抖函数的默认时间间隔
 */
var singleClickInterval = 800L

/**
 * 单击防抖函数，确保固定时间内仅响应首次点击事件，每个控件独立判断
 */
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