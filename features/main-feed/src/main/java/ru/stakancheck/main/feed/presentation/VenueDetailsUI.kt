/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel
import ru.stakancheck.data.models.VenueDetails


@Composable
fun VenueDetailsUI(
    venueId: String,
) {
    val viewModel = koinViewModel<VenueDetailsScreenViewModel>()

    LaunchedEffect(Unit) {
        viewModel.onLoad(venueId)
    }

    VenueDetailsUI(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel
    )
}


@Composable
private fun VenueDetailsUI(
    modifier: Modifier,
    viewModel: VenueDetailsScreenViewModel,
) {
    val state by viewModel.venueDetailsState.collectAsState()

    Scaffold { paddingValues ->
        when (state.venueDetails) {
            null -> {
                Text(text = "Loading")
            }

            else -> {
                VenueDetailsContent(
                    modifier = modifier.padding(paddingValues),
                    venueDetails = state.venueDetails!!
                )
            }
        }
    }
}

@Composable
private fun VenueDetailsContent(
    modifier: Modifier = Modifier,
    venueDetails: VenueDetails,
) {
    Column(

    ) {
        Text(
            text = venueDetails.name,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

