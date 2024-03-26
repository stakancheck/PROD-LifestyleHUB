/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.leisure.list.presentation

import Spacer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import ru.stakancheck.leisure.list.R
import ru.stakancheck.uikit.components.InfoBadge
import ru.stakancheck.uikit.theme.Dimens


@Composable
fun LeisureListUI(
    navigateToVenueDetails: (venueId: String) -> Unit,
    navigateToLeisureEdit: (leisureId: Long?) -> Unit
) {
    LeisureListUI(
        viewModel = koinViewModel(),
        navigateToVenueDetails = navigateToVenueDetails,
        navigateToLeisureEdit = navigateToLeisureEdit,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LeisureListUI(
    viewModel: LeisureListScreenViewModel,
    navigateToVenueDetails: (venueId: String) -> Unit,
    navigateToLeisureEdit: (leisureId: Long?) -> Unit,
) {

    LaunchedEffect(Unit) {
        viewModel.onLoad()
    }

    val updating by viewModel.updating.collectAsState()
    val leisureListState by viewModel.leisureListState.collectAsState()

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text("Leisure List")
                    }
                )
                if (updating)
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth()
                    )
            }
        },
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                leisureListState.ifEmpty {
                    Text(
                        text = stringResource(R.string.label_no_leisures),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(Dimens.spaceMedium)
                    )
                }
                FloatingActionButton(
                    onClick = {
                        navigateToLeisureEdit(null)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Add,
                        contentDescription = null
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(Dimens.spaceMedium),
            verticalArrangement = Arrangement.spacedBy(Dimens.spaceMedium)
        ) {
            items(leisureListState, key = { it.id }) { leisureEntry ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        navigateToLeisureEdit(leisureEntry.id)
                    }
                ) {
                    Column(
                        modifier = Modifier.padding(Dimens.spaceMedium),
                    ) {
                        Text(
                            leisureEntry.title,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Spacer(Dimens.spaceSmall)
                        InfoBadge(
                            label = leisureEntry.formattedDate,
                            icon = Icons.Rounded.CalendarMonth
                        )
                    }
                }
            }
        }
    }
}
