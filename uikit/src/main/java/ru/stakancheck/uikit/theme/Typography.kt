/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import ru.stakancheck.uikit.R


private val appFont: FontFamily = FontFamily(Font(R.font.rubik))
private val appFontHeavy: FontFamily = FontFamily(Font(R.font.rubic_heavy))

private val defaultTypography = Typography()

internal val typography = Typography(
    displayLarge = defaultTypography.displayLarge.copy(fontFamily = appFontHeavy),
    displayMedium = defaultTypography.displayMedium.copy(fontFamily = appFontHeavy),
    displaySmall = defaultTypography.displaySmall.copy(fontFamily = appFont),
    headlineLarge = defaultTypography.headlineLarge.copy(fontFamily = appFontHeavy),
    headlineMedium = defaultTypography.headlineMedium.copy(fontFamily = appFont),
    headlineSmall = defaultTypography.headlineSmall.copy(fontFamily = appFont),
    titleLarge = defaultTypography.titleLarge.copy(fontFamily = appFont),
    titleMedium = defaultTypography.titleMedium.copy(fontFamily = appFont),
    titleSmall = defaultTypography.titleSmall.copy(fontFamily = appFont),
    bodyLarge = defaultTypography.bodyLarge.copy(fontFamily = appFont),
    bodyMedium = defaultTypography.bodyMedium.copy(fontFamily = appFont),
    bodySmall = defaultTypography.bodySmall.copy(fontFamily = appFont),
)
