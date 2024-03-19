package com.bonepeople.android.widget

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * ActivityLifecycleCallbacks with empty default implementations.
 *
 * This interface provides no-op (empty) implementations for all methods of
 * [Application.ActivityLifecycleCallbacks]. It can be used as a base interface
 * for subclasses to override only the methods they need.
 */
interface DefaultActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}