/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.venue.details.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.stakancheck.common.BaseViewModel
import ru.stakancheck.common.Logger
import ru.stakancheck.common.error.DataError
import ru.stakancheck.data.utils.CachebleResult
import ru.stakancheck.venue.details.domain.usecases.GetVenueDetailsByIdUseCase
import ru.stakancheck.venue.details.entities.VenueDetailsUIModel

class VenueDetailsScreenViewModel(
    private val getVenueDetailsByIdUseCase: GetVenueDetailsByIdUseCase,
    private val logger: Logger,
) : BaseViewModel<VenueDetailsScreenViewModel.Action>() {

    private val _venueDetailsState = MutableStateFlow<VenueDetailsState>(VenueDetailsState.None)
    val venueDetailsState: StateFlow<VenueDetailsState> = _venueDetailsState.asStateFlow()


    fun onLoad(venueId: String) {
        viewModelScope.launch {
            refreshVenueDetails(venueId)
        }
    }

    private suspend fun refreshVenueDetails(venueId: String) {
        _venueDetailsState.value = VenueDetailsState.Loading()
        getVenueDetailsByIdUseCase(venueId = venueId)
            .map { it.toState() }
            .stateIn(viewModelScope, SharingStarted.Lazily, VenueDetailsState.None)
            .collect {
                _venueDetailsState.value = it
                logger.d(tag = TAG, "refreshVenueDetails: ${it.venueDetails}")
            }
    }

    private fun CachebleResult<VenueDetailsUIModel, DataError>.toState(): VenueDetailsState {
        return when (this) {
            is CachebleResult.Error -> VenueDetailsState.Error(data)
            is CachebleResult.InProgress -> VenueDetailsState.Loading(data)
            is CachebleResult.Success -> VenueDetailsState.Success(data)
        }
    }

    sealed interface Action {

    }

    sealed class VenueDetailsState(val venueDetails: VenueDetailsUIModel?) {
        data object None : VenueDetailsState(venueDetails = null)

        class Loading(venueDetails: VenueDetailsUIModel? = null) : VenueDetailsState(venueDetails)

        class Error(venueDetails: VenueDetailsUIModel? = null) : VenueDetailsState(venueDetails)

        class Success(venueDetails: VenueDetailsUIModel) : VenueDetailsState(venueDetails)
    }

    companion object {
        const val TAG = "VenueDetailsScreenViewModel"
    }
}
