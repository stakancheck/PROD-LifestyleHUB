/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button as MaterialButton

@Composable
fun SimpleButton(
    text: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    primary: Boolean = true,
    shape: Shape = MaterialTheme.shapes.medium,
    fontWeight: FontWeight = FontWeight.Bold,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    contentPadding: PaddingValues = PaddingValues(16.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: () -> Unit,
) = MaterialButton(
    onClick = onClick,
    modifier = modifier.fillMaxWidth(),
    enabled = enabled,
    shape = shape,
    colors = if (!primary) ButtonDefaults.buttonColors(
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) else ButtonDefaults.buttonColors(),
    elevation = ButtonDefaults.buttonElevation(
        defaultElevation = 6.dp,
        pressedElevation = 0.dp,
        disabledElevation = 0.dp
    ),
    border = null,
    contentPadding = contentPadding,
    interactionSource = interactionSource
) {
    Text(
        text = text,
        fontWeight = fontWeight,
        style = style
    )
}

