/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.components

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import ru.stakancheck.uikit.theme.Radius


fun Modifier.shimmerEffect(
    color: Color,
): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "shimer_transition")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1500)
        ),
        label = "shimmer_modifier"
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color.Transparent,
                color.copy(alpha = 0.3f),
                Color.Transparent
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}


@Composable
fun ShimmerConteiner(
    modifier: Modifier = Modifier,
    color: Color = LocalContentColor.current,
    enabled: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.then(
                if (enabled) {
                    Modifier.shimmerEffect(color = color)
                } else {
                    Modifier
                }
            ),
            content = content
        )
    }
}

@Composable
fun ShimmerPlaceHolder(modifier: Modifier = Modifier, clip: Dp = Radius.medium) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(clip))
            .shimmerEffect(color = LocalContentColor.current)
    )
}
