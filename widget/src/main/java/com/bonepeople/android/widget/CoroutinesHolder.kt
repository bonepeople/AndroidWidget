package com.bonepeople.android.widget

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

object CoroutinesHolder {
    val default: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Default + Job()
    }

    val main: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()
    }
}