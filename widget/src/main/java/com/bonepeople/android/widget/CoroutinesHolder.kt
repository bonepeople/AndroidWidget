package com.bonepeople.android.widget

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Coroutines State Holder
 *
 * Provides global singleton [CoroutineScope] instances for [Dispatchers.Default], [Dispatchers.Main],
 * and [Dispatchers.IO], plus name-based serial execution via [runSerial], [launchSerial], and [asyncSerial].
 *
 * [Documentation](https://github.com/bonepeople/AndroidWidget/tree/main/document/features/CoroutinesHolder)
 */
@Suppress("Unused")
object CoroutinesHolder {
    private val serialMutexMap = ConcurrentHashMap<String, Mutex>()

    /**
     * Global singleton coroutine scope for the Default dispatcher.
     * + Suitable for CPU-intensive tasks.
     * + Uses a shared background thread pool with the number of threads equal to the number of CPU cores.
     */
    val default: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Default + SupervisorJob()
    }

    /**
     * Global singleton coroutine scope for the Main dispatcher.
     * + Executes coroutines on the main thread.
     */
    val main: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.Main + SupervisorJob()
    }

    /**
     * Global singleton coroutine scope for the IO dispatcher.
     * + Suitable for IO-intensive tasks.
     * + Based on a thread pool coroutine dispatcher that automatically adjusts the pool size as needed.
     */
    val io: CoroutineScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext = Dispatchers.IO + SupervisorJob()
    }

    /**
     * Runs the given suspend [block] serially by [name].
     *
     * Tasks with the same [name] will not run at the same time.
     * Tasks with different names are independent and may run concurrently.
     *
     * This method does not switch dispatcher.
     * It runs in the caller's coroutine context.
     *
     * This method does not bind execution to a specific physical thread.
     * It only guarantees coroutine-level mutual exclusion for the same [name].
     *
     * [name] must not be blank.
     *
     * Do not call [runSerial] with the same [name] from within a same-name serial [block]
     * (including inside [launchSerial] / [asyncSerial], which already run the [block] under [runSerial]).
     * The underlying [Mutex] is not reentrant and will deadlock.
     */
    suspend fun <T> runSerial(name: String, block: suspend () -> T): T {
        val mutex = getSerialMutex(name)
        return mutex.withLock { block() }
    }

    /**
     * Launches a serial coroutine by [name].
     *
     * This method can be used outside an existing coroutine scope.
     *
     * Tasks with the same [name] will not run at the same time (via [runSerial]).
     * Tasks with different names are independent and may run concurrently.
     *
     * By default, it uses [default] scope and therefore [Dispatchers.Default].
     * Pass [io] or [main] through [scope] for IO- or UI-bound work.
     *
     * The [block] runs on the dispatcher of [scope]; this method does not switch dispatcher beyond that.
     *
     * [name] must not be blank.
     *
     * Do not call [runSerial] with the same [name] from within the [block]
     * (the [block] already runs under [runSerial]).
     * The underlying [Mutex] is not reentrant and will deadlock.
     */
    fun launchSerial(name: String, scope: CoroutineScope = default, context: CoroutineContext = EmptyCoroutineContext, start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch(context = context, start = start) {
            runSerial(name) { this@launch.block() }
        }
    }

    /**
     * Launches a serial coroutine by [name] and returns a [Deferred].
     *
     * This method can be used outside an existing coroutine scope.
     * Use this when the task has a result; call [Deferred.await] to obtain it.
     *
     * Tasks with the same [name] will not run at the same time (via [runSerial]).
     * Tasks with different names are independent and may run concurrently.
     *
     * By default, it uses [default] scope and therefore [Dispatchers.Default].
     * Pass [io] or [main] through [scope] for IO- or UI-bound work.
     *
     * The [block] runs on the dispatcher of [scope]; this method does not switch dispatcher beyond that.
     *
     * [name] must not be blank.
     *
     * Do not call [runSerial] with the same [name] from within the [block]
     * (the [block] already runs under [runSerial]).
     * The underlying [Mutex] is not reentrant and will deadlock.
     */
    fun <T> asyncSerial(name: String, scope: CoroutineScope = default, context: CoroutineContext = EmptyCoroutineContext, start: CoroutineStart = CoroutineStart.DEFAULT, block: suspend CoroutineScope.() -> T): Deferred<T> {
        return scope.async(context = context, start = start) {
            runSerial(name) { this@async.block() }
        }
    }

    private fun getSerialMutex(name: String): Mutex {
        require(name.isNotBlank()) { "name must not be blank" }

        val existing = serialMutexMap[name]
        if (existing != null) {
            return existing
        }

        val newMutex = Mutex()
        val previous = serialMutexMap.putIfAbsent(name, newMutex)

        return previous ?: newMutex
    }
}