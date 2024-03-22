/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.presentation.components

import Spacer
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import ru.stakancheck.main.feed.R
import ru.stakancheck.main.feed.entities.WeatherBackground
import ru.stakancheck.main.feed.entities.WeatherIcon
import ru.stakancheck.main.feed.entities.WeatherUIModel
import ru.stakancheck.main.feed.entities.WindDirection
import ru.stakancheck.main.feed.utils.WeatherCardColors
import ru.stakancheck.uikit.theme.Dimens
import ru.stakancheck.uikit.theme.LifestyleHUBTheme


@Composable
fun WeatherWidget(
    modifier: Modifier = Modifier,
    weatherModel: WeatherUIModel,
) {

    val contentColor = when (weatherModel.background) {
        WeatherBackground.DAY -> WeatherCardColors.lightBackContent
        WeatherBackground.NIGHT -> WeatherCardColors.darkBackContent
    }

    val contentSurfaceColor = when (weatherModel.background) {
        WeatherBackground.DAY -> WeatherCardColors.lightBackSurfaceContent
        WeatherBackground.NIGHT -> WeatherCardColors.darkBackSurfaceContent
    }

    CompositionLocalProvider(value = LocalContentColor provides contentColor) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    brush = when (weatherModel.background) {
                        WeatherBackground.DAY -> WeatherCardColors.lightGradient
                        WeatherBackground.NIGHT -> WeatherCardColors.darkGradient
                    }
                )
                .padding(Dimens.spaceMedium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = "location"
                    )
                    Spacer(Dimens.spaceExtraSmall)
                    Text(
                        text = weatherModel.location,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Text(
                    text = stringResource(R.string.weather_update_time, weatherModel.updateDate),
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(horizontal = Dimens.spaceLarge),
                contentScale = ContentScale.FillHeight,
                painter = painterResource(weatherModel.weatherIcon.resId),
                contentDescription = weatherModel.weatherCondition
            )

            Column(
                modifier = Modifier.offset(y = -64.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = weatherModel.temp.toString(),
                        style = MaterialTheme.typography.displayLarge
                    )

                    DegreeSign(
                        modifier = Modifier.padding(top = Dimens.spaceMedium),
                        color = contentSurfaceColor
                    )
                }

                Text(
                    text = weatherModel.weatherCondition,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    color = contentSurfaceColor,
                    text = stringResource(R.string.weather_feels_like, weatherModel.feelsLike),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}

@Composable
private fun DegreeSign(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    Canvas(modifier = modifier.size(8.dp)) {
        drawCircle(
            color = color,
            style = Stroke(width = 6F)
        )
    }
}

private class WeatherModelProvider : PreviewParameterProvider<WeatherUIModel> {
    override val values: Sequence<WeatherUIModel> = sequenceOf(
        WeatherUIModel(
            weatherCondition = "Clear Sky",
            weatherIcon = WeatherIcon.CLEAR_SKY_DAY,
            background = WeatherBackground.DAY,
            temp = 20,
            feelsLike = 18,
            pressure = 1013,
            humidity = 45,
            tempMax = 22,
            tempMin = 18,
            updateDate = "21.03 10:00",
            sunriseTime = "06:00",
            sunsetTime = "18:00",
            windSpeed = 5,
            windDirection = WindDirection.NORTH,
            location = "Moscow"
        )
    )
}

@Preview(name = "Weather Widget Preview", showBackground = true)
@Composable
fun WeatherWidgetNightPreview(@PreviewParameter(WeatherModelProvider::class) weatherModel: WeatherUIModel) {
    LifestyleHUBTheme {
        WeatherWidget(weatherModel = weatherModel)
    }
}
