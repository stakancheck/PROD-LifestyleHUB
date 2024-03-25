/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import ru.stakancheck.uikit.utils.indicatorOffsetForPage

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalImagePager(
    pagerState: PagerState,
    imageUrls: List<String>,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        HorizontalPager(
            userScrollEnabled = true,
            state = pagerState
        ) { key ->

            val painter = rememberAsyncImagePainter(
                model = ImageRequest
                    .Builder(LocalContext.current)
                    .diskCacheKey(imageUrls[key])
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .networkCachePolicy(CachePolicy.ENABLED)
                    .data(imageUrls[key])
                    .crossfade(true)
                    .build()
            )

            Box {
                Image(
                    modifier = Modifier
                        .fillMaxSize()
                        .blur(20.dp),
                    painter = painter,
                    contentScale = ContentScale.Crop,
                    contentDescription = contentDescription
                )

                Image(
                    modifier = Modifier
                        .fillMaxSize(),
                    painter = painter,
                    contentScale = ContentScale.FillHeight,
                    contentDescription = contentDescription
                )
            }
        }

        LineIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            pagesCount = imageUrls.size,
            pagerState = pagerState
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LineIndicator(
    modifier: Modifier = Modifier,
    pagesCount: Int,
    pagerState: PagerState
) {
    Row(
        modifier = modifier
            .width(32.dp * pagesCount)
            .height(48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        for (i in 0 until pagesCount) {
            val offset = pagerState.indicatorOffsetForPage(i)
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .weight(.5f + (offset * 3f))
                    .height(8.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.8f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {}
        }
    }
}