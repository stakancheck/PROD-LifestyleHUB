/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.presentation.components

import Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import ru.stakancheck.uikit.theme.Dimens
import ru.stakancheck.uikit.theme.IconSize


@Composable
fun InfoBadge(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    label: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        icon?.let {
            Icon(
                painter = it,
                contentDescription = null,
                modifier = Modifier.size(IconSize.Small)
            )
            Spacer(Dimens.spaceExtraSmall)
        }
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
        )
    }
}
