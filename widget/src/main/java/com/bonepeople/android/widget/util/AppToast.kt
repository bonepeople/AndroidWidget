package com.bonepeople.android.widget.util

import android.widget.Toast
import com.bonepeople.android.widget.ApplicationHolder
import com.bonepeople.android.widget.CoroutinesHolder
import kotlinx.coroutines.launch

/**
 * Toast工具类
 */
object AppToast {
    /**
     * 展示短时间的toast
     */
    fun show(content: CharSequence?) {
        show(content, Toast.LENGTH_SHORT)
    }

    /**
     * 展示指定时间的toast
     * @param duration toast的展示时间：[android.widget.Toast.LENGTH_SHORT]或[android.widget.Toast.LENGTH_LONG]
     */
    fun show(content: CharSequence?, duration: Int) {
        if (content.isNullOrBlank()) return
        CoroutinesHolder.main.launch {
            Toast.makeText(ApplicationHolder.instance, content, duration).show()
        }
    }
}