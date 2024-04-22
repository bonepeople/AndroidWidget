package com.bonepeople.android.widget

import android.app.Activity
import android.os.Bundle
import java.util.*
import kotlin.collections.HashMap

/**
 * Activity State Holder
 *
 * A utility object to manage the state and data associated with [Activity] instances.
 */
@Suppress("UNUSED", "MemberVisibilityCanBePrivate")
object ActivityHolder : DefaultActivityLifecycleCallbacks {
    private val activityData = HashMap<Activity, HashMap<String, Any>>()

    /**
     * List of active [Activity] instances.
     * Maintains all currently opened activities in the order they were launched.
     */
    val activityList = LinkedList<Activity>()

    /**
     * Retrieves the topmost [Activity] instance.
     * Returns `null` if no activities are present.
     */
    fun getTopActivity(): Activity? {
        return if (activityList.isEmpty()) null else activityList.last
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityList.addLast(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        activityList.remove(activity)
        activityData.remove(activity)
    }

    @Deprecated("This extension method pollutes code suggestions. This method will be removed from version 1.7.0.", ReplaceWith("ActivityHolder.putData(this, key, value)", "com.bonepeople.android.widget.ActivityHolder"))
    fun Activity.putExtra(key: String, value: Any) = putData(this, key, value)

    @Deprecated("This extension method pollutes code suggestions. This method will be removed from version 1.7.0.", ReplaceWith("ActivityHolder.getData(this, key)", "com.bonepeople.android.widget.ActivityHolder"))
    fun Activity.getExtra(key: String): Any? = getData(this, key)

    @Deprecated("This extension method pollutes code suggestions. This method will be removed from version 1.7.0.", ReplaceWith("ActivityHolder.removeData(this, key)", "com.bonepeople.android.widget.ActivityHolder"))
    fun Activity.removeExtra(key: String) = removeData(this, key)

    /**
     * Stores a key-value pair in the state of the specified [Activity].
     */
    fun putData(activity: Activity, key: String, value: Any) {
        val map = activityData.getOrPut(activity) { HashMap() }
        map[key] = value
    }

    /**
     * Retrieves the value associated with the given key in the state of the specified [Activity].
     */
    fun getData(activity: Activity, key: String): Any? {
        return activityData[activity]?.get(key)
    }

    /**
     * Removes the data associated with the given key in the state of the specified [Activity].
     */
    fun removeData(activity: Activity, key: String) {
        activityData[activity]?.remove(key)
    }
}