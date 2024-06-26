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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import org.koin.androidx.compose.koinViewModel
import ru.stakancheck.data.models.Interest
import ru.stakancheck.main.feed.R
import ru.stakancheck.main.feed.presentation.components.VenueInterestItem
import ru.stakancheck.main.feed.presentation.components.WeatherWidget
import ru.stakancheck.uikit.components.ShimmerPlaceHolder
import ru.stakancheck.uikit.theme.Dimens
import ru.stakancheck.uikit.utils.observeAsActions

@Composable
fun MainFeedScreen(
    navigateToVenueDetails: (id: String) -> Unit
) {
    MainFeedScreen(
        navigateToVenueDetails = navigateToVenueDetails,
        viewModel = koinViewModel()
    )
}

@Composable
private fun MainFeedScreen(
    navigateToVenueDetails: (id: String) -> Unit,
    viewModel: MainFeedScreenViewModel
) {
    val weatherState by viewModel.weatherState.collectAsState()
    val lazyListState = rememberLazyListState()

    val interestsItems = viewModel.interestsState.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        viewModel.onLoad()
        lazyListState.animateScrollToItem(0)
    }

    viewModel.actions.observeAsActions {
        when (it) {
            is MainFeedScreenViewModel.Action.ShowVenueDetails -> {
                navigateToVenueDetails(it.venueId)
            }
        }
    }

    // TODO: On start scroll to saved state
    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(Dimens.spaceMedium),
        verticalArrangement = Arrangement.spacedBy(Dimens.spaceMedium),
    ) {
        item {
            WeatherWidget(
                modifier = Modifier.fillMaxWidth(),
                weatherState = weatherState
            )
        }

        items(interestsItems.itemCount) { index ->
            when (val interest = interestsItems[index]!!) {
                is Interest.Venue -> {
                    VenueInterestItem(venueModel = interest) {
                        viewModel.onShowVenueDetails(interest.venueId)
                    }
                }
            }
        }

        interestsItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    items(5) {
                        ShimmerPlaceHolder(
                            modifier = Modifier
                                .fillMaxHeight()
                                .height(104.dp)
                        )
                    }
                }

                loadState.append is LoadState.Loading -> {
                    items(5) {
                        ShimmerPlaceHolder(
                            modifier = Modifier
                                .fillMaxHeight()
                                .height(104.dp)
                        )
                    }
                }

                loadState.hasError -> {
                    item {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = stringResource(R.string.interests_not_found),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }
        }
    }
}
