package com.bonepeople.android.widget.util.event

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableSharedFlow

class EventFlow<T> {
    private val flow = MutableSharedFlow<EventInfo<String>>(0, Int.MAX_VALUE)

    fun register(owner: LifecycleOwner, vararg names: String, action: suspend (EventInfo<String>) -> Unit) {
        flow.flowWithLifecycle(owner.lifecycle, Lifecycle.State.CREATED).filter { it.name in names }.onEach(action).launchIn(owner.lifecycleScope)
    }

    suspend fun postEvent(name: String, data: String) {
        flow.emit(EventInfo(name, data))
    }
}