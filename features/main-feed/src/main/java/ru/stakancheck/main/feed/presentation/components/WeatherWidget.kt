/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.presentation.components

import Spacer
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Air
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import ru.stakancheck.main.feed.R
import ru.stakancheck.main.feed.entities.HumidityLevel
import ru.stakancheck.main.feed.entities.WeatherBackground
import ru.stakancheck.main.feed.entities.WeatherIcon
import ru.stakancheck.main.feed.entities.WeatherUIModel
import ru.stakancheck.main.feed.entities.WindDirection
import ru.stakancheck.main.feed.utils.WeatherCardColors
import ru.stakancheck.uikit.components.ShimmerConteiner
import ru.stakancheck.uikit.components.ShimmerPlaceHolder
import ru.stakancheck.uikit.icons.IconPack
import ru.stakancheck.uikit.icons.iconpack.IcHumidityHigh
import ru.stakancheck.uikit.icons.iconpack.IcHumidityLow
import ru.stakancheck.uikit.icons.iconpack.IcHumidityMid
import ru.stakancheck.uikit.theme.Dimens
import ru.stakancheck.uikit.theme.Elevation
import ru.stakancheck.uikit.theme.IconSize
import ru.stakancheck.uikit.theme.LifestyleHUBTheme
import ru.stakancheck.uikit.theme.Radius


@Composable
fun WeatherWidget(
    modifier: Modifier = Modifier,
    loading: Boolean,
    weatherModel: WeatherUIModel?,
) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(Radius.large),
        colors = CardDefaults.elevatedCardColors(
            contentColor = WeatherCardColors.contentColor
        ),
        elevation = CardDefaults.cardElevation(Elevation.defaultElevation)
    ) {
        AnimatedContent(targetState = weatherModel) {
            if (it == null) {
                ShimmerPlaceHolder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(256.dp)
                )
            } else {
                WeatherWidgetContent(weatherModel = it, loading = loading)
            }
        }
    }
}

@Composable
private fun WeatherWidgetContent(
    modifier: Modifier = Modifier,
    loading: Boolean,
    weatherModel: WeatherUIModel,
) {
    ShimmerConteiner(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = when (weatherModel.background) {
                    WeatherBackground.DAY -> WeatherCardColors.lightGradient
                    WeatherBackground.NIGHT -> WeatherCardColors.darkGradient
                }
            ),
        enabled = loading
    ) {
        Column(
            modifier = modifier
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Dimens.spaceMedium),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(IconSize.Small),
                        imageVector = Icons.Rounded.LocationOn,
                        contentDescription = "location"
                    )
                    Spacer(Dimens.spaceExtraSmall)
                    Text(
                        color = WeatherCardColors.surfaceContentColor,
                        text = weatherModel.location,
                        style = MaterialTheme.typography.titleSmall
                    )
                }

                Text(
                    color = WeatherCardColors.surfaceContentColor,
                    text = stringResource(R.string.weather_data_from, weatherModel.updateDate),
                    style = MaterialTheme.typography.titleSmall
                )
            }
            Row(
                modifier = Modifier.padding(horizontal = Dimens.spaceMedium),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth(),
                    contentScale = ContentScale.Fit,
                    painter = painterResource(weatherModel.weatherIcon.resId),
                    contentDescription = weatherModel.weatherCondition
                )

                Spacer(Dimens.spaceLarge)

                Column(
                    modifier = Modifier
                        .weight(3f),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            modifier = Modifier.alignByBaseline(),
                            text = weatherModel.temp.toString(),
                            style = MaterialTheme.typography.displayLarge
                        )

                        DegreeSign(
                            modifier = Modifier.padding(top = Dimens.spaceMedium),
                            color = WeatherCardColors.surfaceContentColor
                        )

                        Spacer(Dimens.spaceMedium)

                        Text(
                            modifier = Modifier.alignByBaseline(),
                            color = WeatherCardColors.surfaceContentColor,
                            text = stringResource(
                                R.string.weather_temperature_range,
                                weatherModel.tempMax,
                                weatherModel.tempMin
                            ),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Text(
                        text = weatherModel.weatherCondition,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        color = WeatherCardColors.surfaceContentColor,
                        text = stringResource(R.string.weather_feels_like, weatherModel.feelsLike),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 142.dp)
                    .alpha(0.6f)
                    .padding(Dimens.spaceMedium),
                shape = RoundedCornerShape(Radius.medium),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = when (weatherModel.background) {
                        WeatherBackground.DAY -> WeatherCardColors.sheetLightColor
                        WeatherBackground.NIGHT -> WeatherCardColors.sheetDarkColor
                    },
                    contentColor = WeatherCardColors.contentColor
                ),
                elevation = CardDefaults.cardElevation(Elevation.defaultElevation)
            ) {
                Row(
                    modifier = Modifier.padding(Dimens.spaceSmall),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WeatherInfoRow(
                        modifier = Modifier.weight(1f),
                        imageVector = Icons.Rounded.Air,
                        title = stringResource(R.string.weather_wind),
                        value =
                        stringResource(
                            R.string.weather_wind_speed,
                            weatherModel.windSpeed
                        ) + ", " + stringResource(
                            weatherModel.windDirection.resId
                        )
                    )

                    Spacer(Dimens.spaceExtraSmall)

                    VerticalDivider(
                        modifier = Modifier.padding(vertical = Dimens.spaceLarge),
                        color = WeatherCardColors.surfaceContentColor,
                    )

                    Spacer(Dimens.spaceExtraSmall)

                    WeatherInfoRow(
                        modifier = Modifier.weight(1f),
                        imageVector = when (weatherModel.humidityLevel) {
                            HumidityLevel.LOW -> IconPack.IcHumidityLow
                            HumidityLevel.MID -> IconPack.IcHumidityMid
                            HumidityLevel.HIGH -> IconPack.IcHumidityHigh
                        },
                        title = stringResource(R.string.weather_humidity),
                        value = stringResource(
                            R.string.weather_humidity_value,
                            weatherModel.humidity
                        )
                    )

                    Spacer(Dimens.spaceExtraSmall)

                    VerticalDivider(
                        modifier = Modifier.padding(vertical = Dimens.spaceLarge),
                        color = WeatherCardColors.surfaceContentColor,
                    )

                    Spacer(Dimens.spaceExtraSmall)

                    WeatherInfoRow(
                        modifier = Modifier.weight(1f),
                        imageVector = Icons.Rounded.Thermostat,
                        title = stringResource(R.string.weather_pressure),
                        value = stringResource(
                            R.string.weather_pressure_value,
                            weatherModel.pressure
                        )
                    )
                }
            }
        }
    }
}


@Composable
private fun WeatherInfoRow(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    title: String,
    value: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            tint = WeatherCardColors.onSheetColor,
            imageVector = imageVector,
            contentDescription = title
        )

        Spacer(Dimens.spaceSmall)

        Text(
            color = WeatherCardColors.onSheetColor,
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(Dimens.spaceExtraSmall)

        Text(
            color = WeatherCardColors.contentColor,
            text = title,
            style = MaterialTheme.typography.bodyMedium
        )
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
            humidityLevel = HumidityLevel.MID,
            tempMax = 22,
            tempMin = 18,
            updateDate = "21.03 10:00",
            sunriseTime = "06:00",
            sunsetTime = "18:00",
            windSpeed = 5,
            windDirection = WindDirection.NORTH,
            location = "Moscow"
        ),
        WeatherUIModel(
            weatherCondition = "Clear Sky",
            weatherIcon = WeatherIcon.SNOW_NIGHT,
            background = WeatherBackground.NIGHT,
            temp = 20,
            feelsLike = 18,
            pressure = 1013,
            humidity = 90,
            humidityLevel = HumidityLevel.HIGH,
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
        WeatherWidget(weatherModel = weatherModel, loading = false)
    }
}
