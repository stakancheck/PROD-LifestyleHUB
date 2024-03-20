/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

/**
 * BaseViewModel is a generic class that extends ViewModel.
 * It provides a base for other ViewModel classes in the application.
 * It manages the communication of the application UI with the business logic.
 *
 * @param Action The type of actions that this ViewModel will handle.
 */
open class BaseViewModel<Action> : ViewModel() {
    // A state flow that represents the updating state of the ViewModel.
    private val _updating = MutableStateFlow(false)

    // A public read-only view of the updating state.
    val updating = _updating.asStateFlow()

    // A channel for actions that the ViewModel will handle.
    private val _actions: Channel<Action> = Channel(Channel.BUFFERED)

    // A public read-only view of the actions channel as a flow.
    val actions: Flow<Action> get() = _actions.receiveAsFlow()

    /**
     * Sets the updating state to true.
     * This should be called when the ViewModel starts updating its state.
     */
    fun onUpdateState() {
        _updating.value = true
    }

    /**
     * Sets the updating state to false.
     * This should be called when the ViewModel finishes updating its state.
     */
    fun onUpdatedState() {
        _updating.value = false
    }

    /**
     * Sends an action to the actions channel.
     * This should be called when there is a new action that the ViewModel should handle.
     *
     * @param action The action to be handled.
     */
    suspend fun intentAction(action: Action) {
        _actions.send(action)
    }
}
