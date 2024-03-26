/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.leisure.edit.presentation

import Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.stakancheck.common.utils.DateTools
import ru.stakancheck.leisure.edit.R
import ru.stakancheck.uikit.components.ActionBadge
import ru.stakancheck.uikit.components.CustomDatePickerDialog
import ru.stakancheck.uikit.components.SimpleButton
import ru.stakancheck.uikit.components.SupportingText
import ru.stakancheck.uikit.components.TextField
import ru.stakancheck.uikit.theme.Dimens
import ru.stakancheck.uikit.utils.observeAsActions
import ru.stakancheck.utils.fields.collectAsMutableState
import androidx.compose.foundation.layout.Spacer as DefaultSpacer

@Composable
fun LeisureEntryEditUI(
    leisureId: Long?,
    interestId: String?,
    navigateBack: () -> Unit,
    navigateToVenue: (venueId: String) -> Unit
) {
    LeisureEntryEditDialog(
        leisureId = leisureId,
        interestId = interestId,
        viewModel = koinViewModel(),
        navigateBack = navigateBack,
        navigateToVenue = navigateToVenue
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LeisureEntryEditDialog(
    leisureId: Long?,
    interestId: String?,
    viewModel: LeisureEntryEditScreenViewModel,
    navigateBack: () -> Unit,
    navigateToVenue: (venueId: String) -> Unit,
) {
    val updating by viewModel.updating.collectAsState()

    val (titleValue, titleSetter) = viewModel.titleField.data.collectAsMutableState()
    val titleError by viewModel.titleField.error.collectAsState()

    val (descriptionValue, descriptionSetter) = viewModel.descriptionField.data.collectAsMutableState()
    val descriptionError by viewModel.descriptionField.error.collectAsState()

    val (dateValue, _) = viewModel.dateField.data.collectAsMutableState()
    val dateError by viewModel.dateField.error.collectAsState()

    val interestLinkState by viewModel.interestLinkState.collectAsState()

    val formattedDate by remember(dateValue) {
        derivedStateOf<String?> {
            dateValue?.let { DateTools.provideFormattedDate(it) }
        }
    }

    var datePickerDialogState by remember {
        mutableStateOf<DatePickerDialogState>(
            DatePickerDialogState.Hidden
        )
    }

    LaunchedEffect(Unit) {
        viewModel.onLoad(leisureId, interestId)
    }

    viewModel.actions.observeAsActions {
        when (it) {
            is LeisureEntryEditScreenViewModel.Action.ShowDatePicker -> {
                datePickerDialogState = DatePickerDialogState.Show(
                    onSelect = it.onSelect,
                    initialDateSeconds = it.initialDateSeconds
                )
            }

            LeisureEntryEditScreenViewModel.Action.NavigateBack -> {
                navigateBack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(
                            if (leisureId == null)
                                R.string.title_create_new_leisure
                            else
                                R.string.title_edit_leisure
                        )
                    )
                },
                actions = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Rounded.Close,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(Dimens.spaceMedium)
            ) {
                TextField(
                    value = titleValue,
                    enabled = !updating,
                    onValueChange = titleSetter,
                    isError = titleError != null,
                    supportingText = SupportingText(titleError?.let { stringResource(it) }),
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(stringResource(R.string.label_title)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                )

                Spacer(Dimens.spaceMedium)

                TextField(
                    value = descriptionValue,
                    enabled = !updating,
                    onValueChange = descriptionSetter,
                    isError = descriptionError != null,
                    supportingText = SupportingText(descriptionError?.let { stringResource(it) }),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(196.dp),
                    placeholder = { Text(stringResource(R.string.label_description)) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrect = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    singleLine = false,
                )

                Spacer(Dimens.spaceMedium)

                TextField(
                    value = formattedDate ?: stringResource(R.string.label_date),
                    readOnly = true,
                    singleLine = true,
                    onValueChange = {},
                    isError = dateError != null,
                    supportingText = SupportingText(dateError?.let { stringResource(it) }),
                    trailingIcon = {
                        IconButton(
                            onClick = viewModel::onEditDate,
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CalendarMonth,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )


                interestLinkState?.let {
                    Spacer(Dimens.spaceLarge)

                    ActionBadge(
                        label = stringResource(R.string.label_linked_interest),
                        actionIcon = Icons.Rounded.Link,
                    ) {
                        navigateToVenue(it.id)
                    }
                }

                DefaultSpacer(modifier = Modifier.weight(1f))

                SimpleButton(
                    text = stringResource(R.string.label_save),
                    enabled = titleError == null && descriptionError == null && !updating,
                    primary = true,
                    onClick = viewModel::onSave
                )
            }
        }
    }

    when (val state = datePickerDialogState) {
        DatePickerDialogState.Hidden -> {}
        is DatePickerDialogState.Show -> {
            CustomDatePickerDialog(
                onDateSelected = { state.onSelect(it) },
                selectedDateSeconds = state.initialDateSeconds,
                onDismiss = { datePickerDialogState = DatePickerDialogState.Hidden },
                title = stringResource(R.string.label_date)
            )
        }
    }
}

private sealed interface DatePickerDialogState {
    data object Hidden : DatePickerDialogState
    data class Show(
        val onSelect: (seconds: Long) -> Unit,
        val initialDateSeconds: Long?
    ) : DatePickerDialogState
}
