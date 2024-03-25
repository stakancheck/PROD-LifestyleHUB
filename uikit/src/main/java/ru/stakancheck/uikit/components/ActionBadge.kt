/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.components

import Spacer
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import ru.stakancheck.uikit.theme.Dimens
import ru.stakancheck.uikit.theme.IconSize


@Composable
fun ActionBadge(
    modifier: Modifier = Modifier,
    label: String,
    actionIcon: ImageVector? = null,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(Dimens.spaceSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            actionIcon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.size(IconSize.Medium)
                )
                Spacer(Dimens.spaceMedium)
            }
            Text(
                text = label,
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Composable
fun IconActionBadge(
    modifier: Modifier = Modifier,
    actionIcon: ImageVector,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(Dimens.spaceMedium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = actionIcon,
                contentDescription = null,
                modifier = Modifier.size(IconSize.Large)
            )
        }
    }
}
