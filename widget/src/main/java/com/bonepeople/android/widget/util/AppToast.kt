package com.bonepeople.android.widget.util

import android.widget.Toast
import com.bonepeople.android.widget.ApplicationHolder
import com.bonepeople.android.widget.CoroutinesHolder
import kotlinx.coroutines.launch

@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object AppToast {
    fun show(content: CharSequence?) {
        show(content, Toast.LENGTH_SHORT)
    }

    fun show(content: CharSequence?, duration: Int) {
        if (content.isNullOrBlank()) return
        CoroutinesHolder.main.launch {
            Toast.makeText(ApplicationHolder.app, content, duration).show()
        }
    }
}