/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.mappers

import ru.stakancheck.common.utils.capitalizeFirstLetter
import ru.stakancheck.data.models.Weather
import ru.stakancheck.main.feed.entities.HumidityLevel
import ru.stakancheck.main.feed.entities.WeatherBackground
import ru.stakancheck.main.feed.entities.WeatherIcon
import ru.stakancheck.main.feed.entities.WeatherUIModel
import ru.stakancheck.main.feed.entities.WindDirection
import ru.stakancheck.main.feed.utils.DateFormatter
import kotlin.math.roundToInt

class WeatherResultToWeatherUIModelMapper {
    companion object {
        operator fun invoke(weather: Weather): WeatherUIModel {
            return WeatherUIModel(
                weatherCondition = weather.description.capitalizeFirstLetter(),
                weatherIcon = mapIconToWeatherIcon(weather.icon),
                background = provideBackgroundType(weather.icon),
                temp = weather.temp.roundToInt(),
                feelsLike = weather.feelsLike.roundToInt(),
                pressure = convertPressureToMmHg(weather.pressure),
                humidity = weather.humidity,
                humidityLevel = provideHumidityLevel(weather.humidity),
                tempMin = weather.tempMin.toInt(),
                tempMax = Math.ceil(weather.tempMax).toInt(),
                updateDate = DateFormatter.timeFormat.format(weather.dt),
                sunriseTime = DateFormatter.timeFormat.format(weather.sunrise),
                sunsetTime = DateFormatter.timeFormat.format(weather.sunset),
                windSpeed = weather.windSpeed,
                windDirection = mapDegreesToWindDirection(weather.windDeg),
                location = weather.location
            )
        }

        private fun mapIconToWeatherIcon(icon: String): WeatherIcon =
            when (icon) {
                "01d" -> WeatherIcon.CLEAR_SKY_DAY
                "01n" -> WeatherIcon.CLEAR_SKY_NIGHT
                "02d" -> WeatherIcon.FEW_CLOUDS_DAY
                "02n" -> WeatherIcon.FEW_CLOUDS_NIGHT
                "03d" -> WeatherIcon.SCATTERED_CLOUDS_DAY
                "03n" -> WeatherIcon.SCATTERED_CLOUDS_NIGHT
                "04d" -> WeatherIcon.BROKEN_CLOUDS_DAY
                "04n" -> WeatherIcon.BROKEN_CLOUDS_NIGHT
                "09d" -> WeatherIcon.SHOWER_RAIN_DAY
                "09n" -> WeatherIcon.SHOWER_RAIN_NIGHT
                "10d" -> WeatherIcon.RAIN_DAY
                "10n" -> WeatherIcon.RAIN_NIGHT
                "11d" -> WeatherIcon.THUNDERSTORM_DAY
                "11n" -> WeatherIcon.THUNDERSTORM_NIGHT
                "13d" -> WeatherIcon.SNOW_DAY
                "13n" -> WeatherIcon.SNOW_NIGHT
                "50d" -> WeatherIcon.MIST_DAY
                "50n" -> WeatherIcon.MIST_NIGHT
                else -> WeatherIcon.CLEAR_SKY_DAY
            }

        private fun provideBackgroundType(icon: String): WeatherBackground =
            if (icon.endsWith("d")) WeatherBackground.DAY else WeatherBackground.NIGHT

        @Suppress("MagicNumber")
        private fun mapDegreesToWindDirection(degrees: Int): WindDirection =
            when (degrees) {
                in 23..67 -> WindDirection.NORTH_EAST
                in 68..112 -> WindDirection.EAST
                in 113..157 -> WindDirection.SOUTH_EAST
                in 158..202 -> WindDirection.SOUTH
                in 203..247 -> WindDirection.SOUTH_WEST
                in 248..292 -> WindDirection.WEST
                in 293..337 -> WindDirection.NORTH_WEST
                else -> WindDirection.NORTH
            }

        @Suppress("MagicNumber")
        private fun convertPressureToMmHg(pressureInHPa: Int): Int {
            return (pressureInHPa * 0.750062).roundToInt()
        }

        @Suppress("MagicNumber")
        private fun provideHumidityLevel(humidity: Int): HumidityLevel =
            when (humidity) {
                in 0..30 -> HumidityLevel.LOW
                in 31..60 -> HumidityLevel.MID
                else -> HumidityLevel.HIGH
            }
    }
}