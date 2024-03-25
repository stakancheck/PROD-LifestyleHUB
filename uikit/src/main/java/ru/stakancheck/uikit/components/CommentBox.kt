/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.stakancheck.uikit.theme.Dimens
import ru.stakancheck.uikit.theme.Radius


@Composable
fun CommentBox(
    modifier: Modifier = Modifier,
    left: Boolean = true,
    text: String,
) {
    Box(
        modifier = modifier
            .background(
                MaterialTheme.colorScheme.surfaceVariant,
                RoundedCornerShape(
                    topStart = Radius.medium,
                    topEnd = Radius.medium,
                    bottomStart = if (!left) Radius.medium else 2.dp,
                    bottomEnd = if (left) Radius.medium else 2.dp
                )
            )
    ) {
        Text(
            modifier = Modifier.padding(
                horizontal = Dimens.spaceMedium,
                vertical = Dimens.spaceSmall
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            text = text,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}