/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.utils

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


object WeatherCardColors {

    val topColorLight = Color(0xFF00B5F2)
    val topColorDark = Color(0xFF222321)
    val lightGradient = Brush.verticalGradient(
        colors = listOf(topColorLight, Color(0xFF005BEC))
    )
    val darkGradient = Brush.verticalGradient(
        colors = listOf(topColorDark, Color(0xFF1F2125))
    )

    val contentColor = Color(0xD8F2F9FF)
    val surfaceContentColor = Color(0xCBC5D5E2)

    val sheetLightColor = Color(0xFF063E6F)
    val sheetDarkColor = Color(0xFFD9E4EE)
    val onSheetColor = Color(0xFFF2F9FF)
}
