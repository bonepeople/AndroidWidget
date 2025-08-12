package com.bonepeople.android.widget.util.event

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bonepeople.android.widget.CoroutinesHolder
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * Lifecycle-aware global event bus based on [MutableSharedFlow].
 *
 * Post [AppEventData] from anywhere, subscribe in [LifecycleOwner] (e.g. Activity, Fragment).
 * Posting is independent of subscriber lifecycle; receiving is gated by [minState].
 * When lifecycle is below [minState], the subscriber stops collecting and events emitted
 * during that period are not replayed on resume. Subscription is cancelled on destroy.
 *
 * Usage:
 * ```
 * // 1. Define an event
 * data class TabChangedEvent(val index: Int) : AppEventData
 *
 * // 2. Subscribe — handle only the events you care about
 * AppEvent.register(this) { event ->
 *     when (event) {
 *         is TabChangedEvent -> switchTab(event.index)
 *         is CommonEvent -> { /* handle by event.name */ }
 *     }
 * }
 *
 * // 3. Post
 * AppEvent.postAsync(TabChangedEvent(0))
 * AppEvent.postAsync(CommonEvent("refresh"))
 * ```
 *
 * Notes:
 * - Non-sticky: subscribers do not receive events posted before registration.
 * - In-process only: event objects are passed by reference within the same process.
 */
@Suppress("Unused")
object AppEvent {
    private val flow = MutableSharedFlow<AppEventData>(0, Int.MAX_VALUE, BufferOverflow.DROP_OLDEST)

    /**
     * Registers a lifecycle-aware subscriber.
     *
     * @param owner The [LifecycleOwner] to bind the subscription to.
     * @param minState Minimum lifecycle state for a subscriber to receive events; defaults to [Lifecycle.State.STARTED].
     *                 When lifecycle drops below this state, the subscriber stops collecting;
     *                 events emitted during the inactive period are not replayed on resume.
     * @param action Suspend callback invoked for each event; use `when` to handle specific event types.
     */
    fun register(owner: LifecycleOwner, minState: Lifecycle.State = Lifecycle.State.STARTED, action: suspend (AppEventData) -> Unit) {
        flow.flowWithLifecycle(owner.lifecycle, minState).onEach(action).launchIn(owner.lifecycleScope)
    }

    /**
     * Posts an event. Must be called from a coroutine.
     *
     * @param event The event to post.
     */
    suspend fun post(event: AppEventData) {
        flow.emit(event)
    }

    /**
     * Posts an event from a non-coroutine context.
     * Dispatches via [CoroutinesHolder.default]; subscribers still receive on the main thread.
     *
     * @param event The event to post.
     */
    fun postAsync(event: AppEventData) {
        CoroutinesHolder.default.launch { post(event) }
    }
}