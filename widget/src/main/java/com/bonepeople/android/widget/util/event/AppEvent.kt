package com.bonepeople.android.widget.util.event

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bonepeople.android.widget.CoroutinesHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

/**
 * Global event bus based on [Channel] and [MutableSharedFlow].
 *
 * Post [AppEventData] from anywhere. Subscribe with a [LifecycleOwner] (for example Activity or Fragment)
 * or with a [CoroutineScope] when the subscriber must stay active in the background.
 * Events are enqueued immediately and dispatched sequentially by a single coroutine.
 * Concurrent posting is thread-safe, and events are dispatched in FIFO order.
 *
 * Receiving on a [LifecycleOwner] is gated by minState. When lifecycle is below minState,
 * the subscriber stops collecting. Events emitted during that period are not
 * replayed on resume. Subscription is canceled on destroy.
 * A scope subscriber is not gated by lifecycle. Cancel the scope or the returned [Job] to stop it.
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
 * - FIFO: events are dispatched in the order they enter the queue.
 * - Non-blocking posting: postAsync returns without waiting for event processing.
 * - Non-sticky broadcasting: events are not replayed to new subscribers.
 * - In-process only: event objects are passed by reference within the same process.
 * - The dispatcher uses [CoroutinesHolder.default] and shares its lifecycle.
 */
@Suppress("Unused")
object AppEvent {
    private val queue = Channel<AppEventData>(Channel.UNLIMITED)
    private val flow = MutableSharedFlow<AppEventData>(replay = 0, extraBufferCapacity = 64, onBufferOverflow = BufferOverflow.SUSPEND)

    init {
        CoroutinesHolder.default.launch {
            for (event in queue) {
                flow.emit(event)
            }
        }
    }

    /**
     * Registers a lifecycle-aware subscriber.
     *
     * @param owner The [LifecycleOwner] to bind the subscription to.
     * @param minState Minimum lifecycle state for receiving events; defaults to [Lifecycle.State.STARTED].
     *                 Events emitted while inactive are not replayed on resume.
     * @param action Suspend callback invoked for each event; use `when` to handle specific event types.
     */
    fun register(owner: LifecycleOwner, minState: Lifecycle.State = Lifecycle.State.STARTED, action: suspend (AppEventData) -> Unit) {
        flow.flowWithLifecycle(owner.lifecycle, minState).onEach(action).launchIn(owner.lifecycleScope)
    }

    /**
     * Registers a subscriber bound to [scope].
     *
     * When [scope] is active, the subscription is established before this method returns.
     * With a regular dispatcher, event handling resumes through the [scope] context after subscription setup.
     * The returned [Job] is cancelled when [scope] is cancelled.
     * Events posted before collection starts are not replayed.
     * Exceptions thrown by [action] follow the propagation and handling policy of [scope].
     *
     * @param scope Scope that owns the subscription.
     * @param action Suspend callback invoked for each event. Handle events serially inside this callback.
     */
    fun register(scope: CoroutineScope, action: suspend (AppEventData) -> Unit): Job {
        return scope.launch(start = CoroutineStart.UNDISPATCHED) {
            flow.onSubscription {
                // The slot is active; suspend once so action cannot run during undispatched setup.
                yield()
            }.collect { event -> action(event) }
        }
    }

    /**
     * Enqueues an event from a coroutine.
     * Returns without waiting for event processing.
     * Events are dispatched asynchronously in FIFO order.
     *
     * @param event The event to post.
     */
    suspend fun post(event: AppEventData) {
        queue.send(event)
    }

    /**
     * Enqueues an event from a non-coroutine context.
     * Returns without waiting for event processing.
     * Safe for concurrent calls from multiple threads.
     *
     * @param event The event to post.
     */
    fun postAsync(event: AppEventData) {
        check(queue.trySend(event).isSuccess) {
            "AppEvent queue is closed"
        }
    }
}