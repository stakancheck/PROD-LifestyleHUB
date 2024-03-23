/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.presentation.components

import Spacer
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import ru.stakancheck.data.models.Interest
import ru.stakancheck.main.feed.R
import ru.stakancheck.uikit.theme.CustomTheme
import ru.stakancheck.uikit.theme.Dimens


@Composable
fun VenueInterestItem(
    modifier: Modifier = Modifier,
    venueModel: Interest.Venue,
    onClick: () -> Unit,
) {
    val context = LocalContext.current

    Card(
        modifier = modifier,
        onClick = onClick,
        shape = MaterialTheme.shapes.large
    ) {

        Box {
            venueModel.photoUrl?.let {
                AsyncImage(
                    model = ImageRequest
                        .Builder(context)
                        .diskCacheKey(venueModel.photoId)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .networkCachePolicy(CachePolicy.ENABLED)
                        .data(it)
                        .crossfade(true)
                        .build(),
                    contentDescription = venueModel.name,
                    modifier = Modifier
                        .heightIn(min = 128.dp, max = 280.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillWidth
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(CustomTheme.colors.pictureSheet)
            ) {
                CompositionLocalProvider(value = LocalContentColor provides CustomTheme.colors.onPictureSheet) {
                    Column(
                        modifier = Modifier
                            .padding(Dimens.spaceMedium)
                    ) {
                        Text(
                            text = venueModel.name,
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(Dimens.spaceExtraSmall)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = venueModel.address,
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                text = stringResource(
                                    R.string.location_distance_meters,
                                    venueModel.distance
                                ),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Spacer(Dimens.spaceSmall)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                        ) {
                            venueModel.categories.forEach { category ->
                                val asyncIconPainter = rememberAsyncImagePainter(category.iconUrl)
                                InfoBadge(
                                    label = category.shortName,
                                    icon = asyncIconPainter
                                )
                            }
                        }
                    }
                }
            }

        }
    }
}
