/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.androidx.compose.koinViewModel
import ru.stakancheck.main.feed.presentation.components.WeatherWidget

@Composable
fun MainFeedScreen() {
    MainFeedScreen(viewModel = koinViewModel())
}

@Composable
private fun MainFeedScreen(viewModel: MainFeedScreenViewModel) {
    val weatherState by viewModel.weatherState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onLoad()
    }

    weatherState?.let {
        WeatherWidget(
            weatherModel = it
        )
    }
}
