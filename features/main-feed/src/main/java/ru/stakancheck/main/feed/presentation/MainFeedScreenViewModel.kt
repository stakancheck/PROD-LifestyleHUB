/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.presentation

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.stakancheck.common.BaseViewModel
import ru.stakancheck.common.Logger
import ru.stakancheck.data.models.Interest
import ru.stakancheck.main.feed.domain.usecases.GetCurrentWeatherUseCase
import ru.stakancheck.main.feed.domain.usecases.GetInterestsUseCase
import ru.stakancheck.main.feed.entities.WeatherUIModel

class MainFeedScreenViewModel(
    private val getWeatherUseCase: GetCurrentWeatherUseCase,
    private val getInterestsUseCase: GetInterestsUseCase,
    private val logger: Logger,
) : BaseViewModel<MainFeedScreenViewModel.Action>() {

    private val _weatherState: MutableStateFlow<WeatherUIModel?> = MutableStateFlow(null)
    val weatherState: StateFlow<WeatherUIModel?> = _weatherState.asStateFlow()

    private val _interestsState: MutableStateFlow<PagingData<Interest>> =
        MutableStateFlow(PagingData.empty())
    val interestsState: StateFlow<PagingData<Interest>> = _interestsState.asStateFlow()

    fun onLoad() {
        viewModelScope.launch {
            updateWeather()
        }
        viewModelScope.launch {
            getInterests()
        }
    }

    fun onUpdateWeatherClicked() {
        viewModelScope.launch {
            updateWeather()
        }
    }

    private suspend fun updateWeather() {
        onUpdateState()
        delay(1000)
        getWeatherUseCase.invoke(lat = 61.691891, long = 50.807930)?.let {
            _weatherState.value = it
        }
        onUpdatedState()
    }

    private suspend fun getInterests() {
        getInterestsUseCase.invoke()
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .collect {
                _interestsState.value = it
            }
    }


    sealed interface Action {

    }
}