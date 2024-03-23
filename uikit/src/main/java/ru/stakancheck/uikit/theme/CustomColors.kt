/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import com.example.compose.custom_onPictureSheet_dark
import com.example.compose.custom_onPictureSheet_light
import com.example.compose.custom_onWeatherDayGradientVariant_dark
import com.example.compose.custom_onWeatherDayGradientVariant_light
import com.example.compose.custom_onWeatherDayGradient_dark
import com.example.compose.custom_onWeatherDayGradient_light
import com.example.compose.custom_onWeatherNightGradientVariant_dark
import com.example.compose.custom_onWeatherNightGradientVariant_light
import com.example.compose.custom_onWeatherNightGradient_dark
import com.example.compose.custom_onWeatherNightGradient_light
import com.example.compose.custom_pictureSheet_dark
import com.example.compose.custom_pictureSheet_light
import com.example.compose.custom_sheetWeatherDay_dark
import com.example.compose.custom_sheetWeatherDay_light
import com.example.compose.custom_sheetWeatherNight_dark
import com.example.compose.custom_sheetWeatherNight_light
import com.example.compose.custom_weatherDayGradientEnd_dark
import com.example.compose.custom_weatherDayGradientEnd_light
import com.example.compose.custom_weatherDayGradientStart_dark
import com.example.compose.custom_weatherDayGradientStart_light
import com.example.compose.custom_weatherNightGradientEnd_dark
import com.example.compose.custom_weatherNightGradientEnd_light
import com.example.compose.custom_weatherNightGradientStart_dark
import com.example.compose.custom_weatherNightGradientStart_light


val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        weatherDayColors = WeatherColors(
            weatherBackgroundGradient = listOf(Color.Unspecified),
            onWeatherBackground = Color.Unspecified,
            onWeatherBackgroundVariant = Color.Unspecified,
            sheetWeatherBackground = Color.Unspecified,
        ),
        weatherNightColors = WeatherColors(
            weatherBackgroundGradient = listOf(Color.Unspecified),
            onWeatherBackground = Color.Unspecified,
            onWeatherBackgroundVariant = Color.Unspecified,
            sheetWeatherBackground = Color.Unspecified,
        ),
        pictureSheet = Color.Unspecified,
        onPictureSheet = Color.Unspecified,
    )
}

@Immutable
data class CustomColors(
    // Weather card colors
    val weatherDayColors: WeatherColors,
    val weatherNightColors: WeatherColors,

    // Picture sheet
    val pictureSheet: Color,
    val onPictureSheet: Color,
)


@Immutable
data class WeatherColors(
    val weatherBackgroundGradient: List<Color>,
    val onWeatherBackground: Color,
    val onWeatherBackgroundVariant: Color,
    val sheetWeatherBackground: Color,
)

val lightCustomColors = CustomColors(
    weatherDayColors = WeatherColors(
        weatherBackgroundGradient = listOf(
            custom_weatherDayGradientStart_light,
            custom_weatherDayGradientEnd_light
        ),
        onWeatherBackground = custom_onWeatherDayGradient_light,
        onWeatherBackgroundVariant = custom_onWeatherDayGradientVariant_light,
        sheetWeatherBackground = custom_sheetWeatherDay_light,
    ),
    weatherNightColors = WeatherColors(
        weatherBackgroundGradient = listOf(
            custom_weatherNightGradientStart_light,
            custom_weatherNightGradientEnd_light
        ),
        onWeatherBackground = custom_onWeatherNightGradient_light,
        onWeatherBackgroundVariant = custom_onWeatherNightGradientVariant_light,
        sheetWeatherBackground = custom_sheetWeatherNight_light,
    ),
    pictureSheet = custom_pictureSheet_light,
    onPictureSheet = custom_onPictureSheet_light,
)

val darkCustomColors = CustomColors(
    weatherDayColors = WeatherColors(
        weatherBackgroundGradient = listOf(
            custom_weatherDayGradientStart_dark,
            custom_weatherDayGradientEnd_dark
        ),
        onWeatherBackground = custom_onWeatherDayGradient_dark,
        onWeatherBackgroundVariant = custom_onWeatherDayGradientVariant_dark,
        sheetWeatherBackground = custom_sheetWeatherDay_dark,
    ),
    weatherNightColors = WeatherColors(
        weatherBackgroundGradient = listOf(
            custom_weatherNightGradientStart_dark,
            custom_weatherNightGradientEnd_dark
        ),
        onWeatherBackground = custom_onWeatherNightGradient_dark,
        onWeatherBackgroundVariant = custom_onWeatherNightGradientVariant_dark,
        sheetWeatherBackground = custom_sheetWeatherNight_dark,
    ),
    pictureSheet = custom_pictureSheet_dark,
    onPictureSheet = custom_onPictureSheet_dark,
)


object CustomTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
}
