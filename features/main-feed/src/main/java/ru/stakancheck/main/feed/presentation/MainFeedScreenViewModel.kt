/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.stakancheck.common.BaseViewModel
import ru.stakancheck.common.Logger
import ru.stakancheck.main.feed.domain.usecases.GetCurrentWeatherUseCase
import ru.stakancheck.main.feed.entities.WeatherUIModel

class MainFeedScreenViewModel(
    private val getWeatherUseCase: GetCurrentWeatherUseCase,
    private val logger: Logger,
) : BaseViewModel<MainFeedScreenViewModel.Action>() {

    private val _weatherState: MutableStateFlow<WeatherUIModel?> = MutableStateFlow(null)
    val weatherState: StateFlow<WeatherUIModel?> = _weatherState.asStateFlow()

    fun onLoad() {
        updateWeather()
    }

    private fun updateWeather() {
        viewModelScope.launch {
            getWeatherUseCase.invoke(lat = 61.691891, long = 50.807930)?.let {
                _weatherState.value = it
            }
        }
    }


    sealed interface Action {

    }
}