/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.components

import Spacer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Error
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import ru.stakancheck.common.error.Error
import ru.stakancheck.common.error.getDescriptionResourseId
import ru.stakancheck.common.error.getTitleResourseId
import ru.stakancheck.uikit.theme.Dimens
import ru.stakancheck.uikit.utils.observeAsActions


@Stable
class ToastErrorPresenter(
    private val duration: Long = PresentDuration.LONG
) : ErrorPresenter() {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override operator fun invoke() {
        var errorData by remember { mutableStateOf<Error?>(null) }
        var hidden by remember { mutableStateOf(false) }

        error.observeAsActions {
            it?.let {
                errorData = it
                hidden = false
            }
        }

        LaunchedEffect(hidden) {
            if (!hidden) {
                delay(duration)
                hidden = true
            }
        }

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedVisibility(
                visible = (errorData != null) && !hidden,
                modifier = Modifier.align(Alignment.TopCenter),
                enter = fadeIn(tween(300)) + slideIn(initialOffset = {
                    IntOffset(
                        x = 0,
                        y = -100
                    )
                }),
                exit = fadeOut(tween(500)) + slideOut(targetOffset = { IntOffset(x = 0, y = -100) })
            ) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Dimens.spaceMedium),
                    shape = MaterialTheme.shapes.medium,
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer,
                        contentColor = MaterialTheme.colorScheme.onErrorContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Dimens.spaceMedium)
                    ) {
                        Row {
                            Icon(
                                imageVector = Icons.Rounded.Error,
                                contentDescription = "Error"
                            )
                            Spacer(Dimens.spaceSmall)
                            errorData?.getTitleResourseId()?.let {
                                Text(
                                    text = stringResource(it),
                                    style = MaterialTheme.typography.titleMedium
                                )
                            }
                        }
                        Spacer(Dimens.spaceMedium)
                        errorData?.getDescriptionResourseId()?.let {
                            Text(
                                text = stringResource(it),
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        object PresentDuration {
            const val LONG = 3_000L
        }
    }

}
