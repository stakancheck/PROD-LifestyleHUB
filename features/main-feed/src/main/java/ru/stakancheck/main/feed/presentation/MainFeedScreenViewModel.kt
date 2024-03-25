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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import ru.stakancheck.common.BaseViewModel
import ru.stakancheck.common.Logger
import ru.stakancheck.data.models.Interest
import ru.stakancheck.data.utils.Result
import ru.stakancheck.main.feed.domain.usecases.GetCurrentWeatherUseCase
import ru.stakancheck.main.feed.domain.usecases.GetInterestsUseCase
import ru.stakancheck.main.feed.entities.WeatherUIModel

class MainFeedScreenViewModel(
    private val getWeatherUseCase: GetCurrentWeatherUseCase,
    private val getInterestsUseCase: GetInterestsUseCase,
    private val logger: Logger,
) : BaseViewModel<MainFeedScreenViewModel.Action>() {

    private val _weatherState: MutableStateFlow<WeatherState> =
        MutableStateFlow(WeatherState.Loading())
    val weatherState: StateFlow<WeatherState> = _weatherState.asStateFlow()

    private val _interestsState: MutableStateFlow<PagingData<Interest>> =
        MutableStateFlow(PagingData.empty())
    val interestsState: StateFlow<PagingData<Interest>> = _interestsState.asStateFlow()


    fun onLoad() {
        logger.d(tag = TAG, "onLoad")
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

    fun onShowVenueDetails(venueId: String) {
        viewModelScope.launch {
            intentAction(Action.ShowVenueDetails(venueId = venueId))
        }
    }

    private suspend fun updateWeather() {
        _weatherState.value =
            WeatherState.Loading(weather = (_weatherState.value as? WeatherState.Success)?.weather)
        when (val result = getWeatherUseCase.invoke()) {
            is Result.Error -> _weatherState.value = WeatherState.Error
            is Result.Success -> _weatherState.value = WeatherState.Success(result.data)
        }
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
        data class ShowVenueDetails(val venueId: String) : Action
    }

    sealed interface WeatherState {
        data class Loading(val weather: WeatherUIModel? = null) : WeatherState
        data class Success(val weather: WeatherUIModel) : WeatherState
        data object Error : WeatherState
    }

    companion object {
        const val TAG = "MainFeedScreenViewModel"
    }

}