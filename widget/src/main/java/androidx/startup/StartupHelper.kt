package androidx.startup

import android.app.Application
import android.content.Context

/**
 * Helper utility class for Startup initialization.
 * + By default, Startup performs automatic initialization only in the main process.
 *   For applications with multiple processes, this utility class can be used to manually trigger the initialization logic.
 */
@Suppress("Unused")
object StartupHelper {
    /**
     * Initializes all defined [Initializer] instances.
     * + Repeated calls will not affect already initialized objects.
     * + It is recommended to call this method in [Application.onCreate]
     *   to ensure that initialization is completed before usage.
     */
    fun initializeAll(context: Context) {
        AppInitializer.getInstance(context).discoverAndInitialize()
    }
}