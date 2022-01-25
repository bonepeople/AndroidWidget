package com.bonepeople.android.widget

import android.app.Activity
import android.os.Bundle
import java.util.*
import kotlin.collections.HashMap

object ActivityHolder : DefaultActivityLifecycleCallbacks {
    private val activityData = HashMap<Activity, HashMap<String, Any>>()
    val activityList = LinkedList<Activity>()

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

    fun Activity.putExtra(key: String, value: Any) {
        val map = activityData.getOrPut(this) { HashMap() }
        map[key] = value
    }

    fun Activity.getExtra(key: String): Any? {
        return activityData[this]?.get(key)
    }

    fun Activity.removeExtra(key: String) {
        activityData[this]?.remove(key)
    }
}