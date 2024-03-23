/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.common.permission

import android.app.Activity
import android.content.Context
import androidx.annotation.MainThread
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.withContext
import ru.stakancheck.common.utils.isAtLeastStarted
import ru.stakancheck.common.utils.isPermissionDenied
import java.lang.ref.WeakReference
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume

private typealias PermissionResult = Map<String, Boolean>
private typealias ResultCallback = (PermissionResult) -> Unit
private typealias ActivityWeakRef = WeakReference<PermissionCheckerActivity>
private typealias ActivityCallback = (PermissionCheckerActivity) -> Unit


/**
 * A class that checks permissions using the Result API.
 *
 * @property context The application context.
 * @property mainDispatcher The dispatcher to use for main-safe coroutines.
 */
class PermissionCheckerImpl(
    private val context: Context,
    private val mainDispatcher: CoroutineContext = Dispatchers.Main.immediate,
) : PermissionChecker {

    private val mutex = Mutex()

    private var activityWeakRef: ActivityWeakRef? = null

    private var resultCallback: ResultCallback? = null

    private var onNewActivityCallback: ActivityCallback? = null

    /**
     * Stores [activity] using a [WeakReference]. Call it on [Activity.onStart]
     *
     * @param activity The activity to attach.
     */
    @MainThread
    fun attach(activity: PermissionCheckerActivity) {
        activityWeakRef = WeakReference(activity)
        onNewActivityCallback?.invoke(activity)
    }

    /**
     * Accepts permission result from an activity
     *
     * @param result The result of the permission check.
     */
    @MainThread
    fun onPermissionResult(result: PermissionResult) {
        resultCallback?.invoke(result)
    }

    /**
     * Checks the permissions
     * @see [PermissionChecker.checkPermissions]
     *
     * @param permissions The permissions to check.
     * @return A [Result] indicating whether the permissions were granted.
     */
    override suspend fun checkPermissions(vararg permissions: String): Result<Unit> =
        if (permissions.any(context::isPermissionDenied)) {

            mutex.lock()
            try {
                val result: PermissionResult = withContext(mainDispatcher) {
                    var activity: PermissionCheckerActivity? = awaitForStartedActivity()

                    val result: PermissionResult = suspendCancellableCoroutine {
                        resultCallback = it::resume
                        it.invokeOnCancellation { resultCallback = null }
                        activity?.resultLauncher?.launch(arrayOf(*permissions))
                        activity = null // Preventing memory leak
                    }

                    resultCallback = null

                    result
                }

                if (result.all { it.value }) {
                    Result.success(Unit)
                } else {
                    val deniedPermissions = result
                        .entries
                        .asSequence()
                        .filter { !it.value }
                        .map { it.key }
                        .toSet()

                    Result.failure(PermissionChecker.PermissionsDeniedException(deniedPermissions))
                }
            } catch (throwable: Throwable) {
                Result.failure(throwable)
            } finally {
                mutex.unlock()
            }
        } else {
            Result.success(Unit)
        }

    /**
     * Awaits for attached and started activity
     *
     * @return The activity that is at least started.
     */
    private suspend fun awaitForStartedActivity(): PermissionCheckerActivity {
        val activity = activityWeakRef?.get()

        return if (activity?.isAtLeastStarted == true) {
            activity
        } else {
            val newActivity = suspendCancellableCoroutine {
                it.invokeOnCancellation { onNewActivityCallback = null }
                onNewActivityCallback = it::resume
            }
            onNewActivityCallback = null

            newActivity
        }
    }
}
