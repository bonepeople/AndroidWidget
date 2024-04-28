package com.bonepeople.android.widget

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Coroutines State Holder
 *
 * Provides global coroutine singletons for different dispatchers to manage coroutine contexts efficiently.
 */
@Suppress("Unused")
object CoroutinesHolder {
    /**
     * Global singleton coroutine scope for the Default dispatcher.
     * + Suitable for CPU-intensive tasks.
     * + Uses a shared background thread pool with the number of threads equal to the number of CPU cores.
     */
    val default: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Default + Job()
    }

    /**
     * Global singleton coroutine scope for the Main dispatcher.
     * + Executes coroutines on the main thread.
     */
    val main: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Main + Job()
    }

    /**
     * Global singleton coroutine scope for the IO dispatcher.
     * + Suitable for IO-intensive tasks.
     * + Based on a thread pool coroutine dispatcher that automatically adjusts the pool size as needed.
     */
    val io: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.IO + Job()
    }
}