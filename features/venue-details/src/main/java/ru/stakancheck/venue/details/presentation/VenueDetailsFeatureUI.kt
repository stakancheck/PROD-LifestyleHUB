/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.venue.details.presentation

import Spacer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Language
import androidx.compose.material.icons.rounded.PinDrop
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.koinViewModel
import ru.stakancheck.uikit.components.ActionBadge
import ru.stakancheck.uikit.components.CommentBox
import ru.stakancheck.uikit.components.HorizontalImagePager
import ru.stakancheck.uikit.components.IconActionBadge
import ru.stakancheck.uikit.components.OutlineBadge
import ru.stakancheck.uikit.components.ShimmerConteiner
import ru.stakancheck.uikit.icons.IconPack
import ru.stakancheck.uikit.icons.iconpack.IcFacebook
import ru.stakancheck.uikit.icons.iconpack.IcTwitter
import ru.stakancheck.uikit.theme.Dimens
import ru.stakancheck.uikit.utils.actionPhoneNumber
import ru.stakancheck.uikit.utils.actionView
import ru.stakancheck.venue.details.R
import ru.stakancheck.venue.details.entities.VenueDetailsUIModel


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

    Box(
        modifier = modifier
    ) {
        when {
            state is VenueDetailsScreenViewModel.VenueDetailsState.Loading -> {
                LinearProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .fillMaxWidth()
                )
            }
        }

        state.venueDetails?.let {
            VenueDetailsContent(
                venueDetails = it,
                loading = (state is VenueDetailsScreenViewModel.VenueDetailsState.Loading ||
                        state is VenueDetailsScreenViewModel.VenueDetailsState.None)
            )
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(Dimens.spaceLarge),
            onClick = { }
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = stringResource(R.string.venue_details_save_to_my_leisure)
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VenueDetailsContent(
    modifier: Modifier = Modifier,
    venueDetails: VenueDetailsUIModel,
    loading: Boolean = false,
) {
    val scrollState = rememberScrollState()
    val pagerState = rememberPagerState {
        venueDetails.photoUrls.size
    }

    Column(
        modifier = modifier.verticalScroll(scrollState)
    ) {
        HorizontalImagePager(
            modifier = Modifier
                .height(240.dp)
                .graphicsLayer {
                    alpha = 1f - ((scrollState.value.toFloat() / 240.dp.toPx()) * 1.5f)
                    translationY = 0.5f * scrollState.value
                },
            pagerState = pagerState,
            imageUrls = venueDetails.photoUrls,
            contentDescription = venueDetails.name
        )

        VenueDetailsInfo(
            modifier = Modifier.fillMaxWidth(),
            venueDetails = venueDetails,
            loading = loading
        )
    }
}

@Composable
private fun VenueDetailsInfo(
    modifier: Modifier = Modifier,
    venueDetails: VenueDetailsUIModel,
    loading: Boolean = false,
) {
    val context = LocalContext.current

    ShimmerConteiner(
        modifier = modifier.background(MaterialTheme.colorScheme.background),
        enabled = loading
    ) {
        Column(
            modifier = Modifier.padding(Dimens.spaceMedium)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    modifier = Modifier
                        .heightIn(max = 64.dp)
                        .weight(1f),
                    text = venueDetails.name,
                    style = MaterialTheme.typography.titleLarge,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ThumbUp,
                        contentDescription = null
                    )
                    Spacer(Dimens.spaceSmall)
                    Text(
                        text = venueDetails.likesCount.toString(),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }

            venueDetails.description?.let {
                Spacer(Dimens.spaceSmall)

                Text(
                    modifier = Modifier.heightIn(max = 64.dp),
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Dimens.spaceSmall)

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Dimens.spaceSmall)
            ) {
                items(venueDetails.categories, key = { it.id }) {
                    val asyncIconPainter = rememberAsyncImagePainter(it.iconUrl)
                    OutlineBadge(
                        label = it.shortName,
                        icon = asyncIconPainter
                    )
                }
            }

            Spacer(Dimens.spaceMedium)

            ActionBadge(
                modifier = Modifier.fillMaxWidth(),
                label = venueDetails.location.address,
                actionIcon = Icons.Rounded.PinDrop
            ) {
                actionView(context, venueDetails.location.url)
            }

            venueDetails.phone?.let {
                Spacer(Dimens.spaceSmall)

                ActionBadge(
                    modifier = Modifier.fillMaxWidth(),
                    label = it.phone ?: stringResource(R.string.venue_details_call),
                    actionIcon = Icons.Rounded.Call
                ) {
                    actionPhoneNumber(context, it.callUrl)
                }
            }

            Spacer(Dimens.spaceMedium)

            Row(
                horizontalArrangement = Arrangement.spacedBy(Dimens.spaceSmall)
            ) {
                venueDetails.url?.let {
                    IconActionBadge(
                        actionIcon = Icons.Rounded.Language
                    ) {
                        actionView(context, it)
                    }
                }

                venueDetails.contacts.forEach {
                    IconActionBadge(
                        actionIcon = when (it) {
                            is VenueDetailsUIModel.Contact.Facebook -> IconPack.IcFacebook
                            is VenueDetailsUIModel.Contact.Twitter -> IconPack.IcTwitter
                        }
                    ) {
                        actionView(context, it.url)
                    }
                }
            }

            Spacer(Dimens.spaceLarge)

            if (venueDetails.reasons.isNotEmpty() || venueDetails.phrases.isNotEmpty()) {
                Text(
                    text = stringResource(R.string.venue_details_visit_reasons),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(Dimens.spaceMedium)

            venueDetails.reasons.forEach {
                CommentBox(
                    text = it
                )
                Spacer(Dimens.spaceSmall)
            }

            venueDetails.phrases.forEach {
                CommentBox(
                    text = it
                )
                Spacer(Dimens.spaceSmall)
            }

            Spacer(96.dp)

        }
    }
}

