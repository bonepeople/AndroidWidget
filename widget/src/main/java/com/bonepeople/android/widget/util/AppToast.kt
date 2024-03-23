package com.bonepeople.android.widget.util

import android.widget.Toast
import com.bonepeople.android.widget.ApplicationHolder
import com.bonepeople.android.widget.CoroutinesHolder
import kotlinx.coroutines.launch

/**
 * Utility class for displaying Toast messages
 */
@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object AppToast {
    /**
     * Displays a Toast for a short duration.
     */
    fun show(content: CharSequence?) {
        show(content, Toast.LENGTH_SHORT)
    }

    /**
     * Displays a Toast for a specified duration.
     * @param duration Duration of the Toast: [android.widget.Toast.LENGTH_SHORT] or [android.widget.Toast.LENGTH_LONG]
     */
    fun show(content: CharSequence?, duration: Int) {
        if (content.isNullOrBlank()) return
        CoroutinesHolder.main.launch {
            Toast.makeText(ApplicationHolder.app, content, duration).show()
        }
    }
}