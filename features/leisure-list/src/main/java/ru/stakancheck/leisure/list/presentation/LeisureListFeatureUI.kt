/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.leisure.list.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import ru.stakancheck.uikit.theme.Dimens


@Composable
fun LeisureListUI(
    navigateToVenueDetails: (venueId: String) -> Unit,
) {
    LeisureListUI(
        viewModel = koinViewModel(),
        navigateToVenueDetails = navigateToVenueDetails
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LeisureListUI(
    viewModel: LeisureListScreenViewModel,
    navigateToVenueDetails: (venueId: String) -> Unit,
) {

    val leisureListState by viewModel.leisureListState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Leisure List")
                }
            )
        },
        floatingActionButton = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                leisureListState.ifEmpty {
                    Text(
                        text = "Нет записей, самое время создать",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(Dimens.spaceMedium)
                    )
                }
                FloatingActionButton(
                    onClick = {}
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
                Card {
                    Text(
                        leisureEntry.title
                    )
                }
            }
        }
    }
}
