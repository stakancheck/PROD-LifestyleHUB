/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import ru.stakancheck.main.feed.presentation.components.WeatherWidget
import ru.stakancheck.uikit.theme.Dimens

@Composable
fun MainFeedScreen() {
    MainFeedScreen(viewModel = koinViewModel())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainFeedScreen(viewModel: MainFeedScreenViewModel) {
    val weatherState by viewModel.weatherState.collectAsState()
    val lazyListState = rememberLazyListState()
    val updating by viewModel.updating.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onLoad()
        lazyListState.animateScrollToItem(0)
    }

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(Dimens.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.spaceMedium),
    ) {
        item {
            WeatherWidget(
                modifier = Modifier.fillMaxWidth(),
                loading = updating,
                weatherModel = weatherState
            )
        }

        items(10) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
            ) {

            }
        }
    }
}
