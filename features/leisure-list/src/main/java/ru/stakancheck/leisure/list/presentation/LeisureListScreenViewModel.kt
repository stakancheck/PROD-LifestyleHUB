/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.leisure.list.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.stakancheck.common.BaseViewModel
import ru.stakancheck.common.Logger
import ru.stakancheck.data.utils.Result
import ru.stakancheck.leisure.list.domain.usecases.GetLeisureListUseCase
import ru.stakancheck.leisure.list.entities.LeisureEntryUIModel

class LeisureListScreenViewModel(
    private val getLeisureListUseCase: GetLeisureListUseCase,
    private val logger: Logger,
) : BaseViewModel<LeisureListScreenViewModel.Action>() {

    private val _leisureListState: MutableStateFlow<List<LeisureEntryUIModel>> =
        MutableStateFlow(emptyList())
    val leisureListState: StateFlow<List<LeisureEntryUIModel>> = _leisureListState.asStateFlow()

    fun onLoad() {
        logger.d(tag = TAG, "onLoad")
        viewModelScope.launch {
            onUpdateState()
            when (val result = getLeisureListUseCase.invoke()) {
                is Result.Error -> {}
                is Result.Success -> {
                    _leisureListState.value = result.data
                }
            }
            onUpdatedState()
        }
    }

    sealed interface Action {}

    companion object {
        const val TAG = "LeisureListScreenViewModel"
    }
}