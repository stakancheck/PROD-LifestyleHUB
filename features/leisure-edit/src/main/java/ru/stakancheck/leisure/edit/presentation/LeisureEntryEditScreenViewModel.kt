/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.leisure.edit.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.stakancheck.common.BaseViewModel
import ru.stakancheck.common.Logger
import ru.stakancheck.data.models.InterestLink
import ru.stakancheck.data.models.LeisureEntry
import ru.stakancheck.data.utils.Result
import ru.stakancheck.leisure.edit.R
import ru.stakancheck.leisure.edit.domain.usecases.GetLeisureEntryUseCase
import ru.stakancheck.leisure.edit.domain.usecases.SaveLeisureEntryUseCase
import ru.stakancheck.uikit.utils.fields.validators.ValidationResult
import ru.stakancheck.uikit.utils.fields.validators.notBlank
import ru.stakancheck.uikit.utils.fields.validators.notNull
import ru.stakancheck.uikit.utils.fields.validators.validateAll
import ru.stakancheck.utils.fields.FormField
import ru.stakancheck.utils.fields.flowBlock
import java.time.Instant
import java.util.Date

class LeisureEntryEditScreenViewModel(
    private val getLeisureEntryUseCase: GetLeisureEntryUseCase,
    private val saveLeisureEntryUseCase: SaveLeisureEntryUseCase,
    private val logger: Logger,
) : BaseViewModel<LeisureEntryEditScreenViewModel.Action>() {

    val titleField: FormField<String, Int> = FormField(
        scope = viewModelScope,
        initialValue = "",
        validation = flowBlock { title ->
            ValidationResult.of(title) {
                notBlank(R.string.field_error_title_cant_be_empty)
            }
        }
    )

    val descriptionField: FormField<String, Int> = FormField(
        scope = viewModelScope,
        initialValue = "",
        validation = flowBlock { description ->
            ValidationResult.of(description) {
                notBlank(R.string.field_error_title_cant_be_empty)
            }
        }
    )

    val dateField: FormField<Date?, Int> = FormField(
        scope = viewModelScope,
        initialValue = null,
        validation = flowBlock { date ->
            ValidationResult.of(date) {
                notNull(R.string.field_error_title_cant_be_empty)
            }
        }
    )

    private val _interestLinkState: MutableStateFlow<InterestLink?> = MutableStateFlow(null)
    val interestLinkState: StateFlow<InterestLink?> = _interestLinkState.asStateFlow()

    private var _leisureId: Long? = null

    fun onLoad(leisureId: Long?, interestId: String?) {
        logger.d(tag = TAG, "onLoad with leisure -> $leisureId interestId -> $interestId")
        _interestLinkState.value = interestId?.let { InterestLink.Venue(it) }
        leisureId?.let {
            viewModelScope.launch {
                _leisureId = leisureId

                onUpdateState()
                when (val result = getLeisureEntryUseCase.invoke(leisureId)) {
                    is Result.Error -> {}
                    is Result.Success -> {
                        titleField.setValue(result.data.title)
                        descriptionField.setValue(result.data.description)
                        _interestLinkState.value = result.data.interestLink
                        dateField.setValue(result.data.date)
                    }
                }
                onUpdatedState()
            }
        }
    }

    fun onEditDate() {
        viewModelScope.launch {
            intentAction(
                Action.ShowDatePicker(
                    onSelect = { seconds ->
                        dateField.setValue(
                            Date.from(Instant.ofEpochSecond(seconds))
                        )
                    },
                    initialDateSeconds = dateField.value()?.time?.div(1000)
                )
            )
        }
    }

    fun onSave() {
        logger.d(tag = TAG, "onSave")
        viewModelScope.launch {
            onUpdateState()
            if (validateAll(titleField, descriptionField)) {
                saveLeisureEntryUseCase.invoke(
                    leisureEntry = LeisureEntry(
                        id = _leisureId,
                        title = titleField.value(),
                        description = descriptionField.value(),
                        date = dateField.value() ?: Date(),
                        interestLink = _interestLinkState.value,
                        ownerId = "test"
                    )
                )
            }
            intentAction(Action.NavigateBack)
            onUpdatedState()
        }
    }

    sealed interface Action {
        data class ShowDatePicker(
            val onSelect: (seconds: Long) -> Unit,
            val initialDateSeconds: Long? = null
        ) : Action

        data object NavigateBack : Action
    }

    companion object {
        const val TAG = "LeisureEntryEditScreenViewModel"
    }
}
