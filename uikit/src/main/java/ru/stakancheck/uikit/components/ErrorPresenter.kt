/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.components

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import ru.stakancheck.common.error.Error
import ru.stakancheck.common.error.ErrorObserver

@Stable
abstract class ErrorPresenter : ErrorObserver {
    private val _eror = Channel<Error?>(Channel.BUFFERED)
    protected val error = _eror.receiveAsFlow()

    @Composable
    open operator fun invoke() {
    }

    override fun onError(error: Error) {
        Log.d("ErrorPresenter", "onError: $error")
        _eror.trySend(error)
    }
}
