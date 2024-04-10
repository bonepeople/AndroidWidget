package androidx.startup

import android.content.Context

@Suppress("UNUSED")
object StartupHelper {
    fun initializeAll(context: Context) {
        AppInitializer.getInstance(context).discoverAndInitialize()
    }
}