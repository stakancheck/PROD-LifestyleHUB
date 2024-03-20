/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.presentation.feature_main_feed

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.stakancheck.lifestylehub.presentation.base.BaseViewModel

class MainFeedViewModel : BaseViewModel<MainFeedViewModel.Action>() {
    private val _weatherState = MutableStateFlow(null)
    val weatherState = _weatherState.asStateFlow()

    fun onLoad() {
        viewModelScope.launch {
        }
    }

    sealed interface Action {
        data class NavigateToFeedDetails(val id: Long) : Action
    }
}