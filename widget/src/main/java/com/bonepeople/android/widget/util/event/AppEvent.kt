package com.bonepeople.android.widget.util.event

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableSharedFlow

class AppEvent {
    object Event {
        const val CHANGE_MAIN_FRAGMENT = "CHANGE_MAIN_FRAGMENT"
        const val GO_HOME_TOP = "GO_HOME_TOP"
    }
}