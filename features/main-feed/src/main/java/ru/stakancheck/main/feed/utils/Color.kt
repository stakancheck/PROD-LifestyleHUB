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
    val lightGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF00B5F2), Color(0xFF005BEC))
    )

    val darkGradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF222321), Color(0xFF1F2125))
    )

    val lightBackContent = Color(0xCD051A41)
    val darkBackContent = Color(0xD8F2F9FF)
    val lightBackSurfaceContent = Color(0xCD1B2B49)
    val darkBackSurfaceContent = Color(0xD85E768A)
}
