/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.main.feed.entities

import ru.stakancheck.main.feed.R


/**
 * Data class representing the UI model for weather.
 *
 * @property weatherCondition The current weather condition.
 * @property weatherIcon The icon representing the current weather condition.
 * @property background The background representing the current weather condition.
 * @property icon The URL of the icon representing the current weather condition.
 * @property temp The current temperature.
 * @property feelsLike The temperature as it feels like.
 * @property pressure The current atmospheric pressure.
 * @property humidity The current humidity level.
 * @property tempRange The range of temperature for the day.
 * @property updateDate The date when the weather data was last updated.
 * @property sunriseTime The time of sunrise.
 * @property sunsetTime The time of sunset.
 * @property windSpeed The speed of the wind.
 * @property windDirection The direction of the wind.
 * @property location The location for which the weather data is.
 */
data class WeatherUIModel(
    val weatherCondition: String,
    val weatherIcon: WeatherIcon,
    val background: WeatherBackground,
    val icon: String,
    val temp: String,
    val feelsLike: String,
    val pressure: String,
    val humidity: String,
    val tempRange: String,
    val updateDate: String,
    val sunriseTime: String,
    val sunsetTime: String,
    val windSpeed: String,
    val windDirection: WindDirection,
    val location: String,
)


/**
 * Enum class representing different weather icons.
 *
 * Each enum constant represents a different weather condition and is associated with a drawable resource ID.
 */
enum class WeatherIcon(resId: Int) {
    CLEAR_SKY_DAY(R.drawable.clear_sky_day),
    CLEAR_SKY_NIGHT(R.drawable.clear_sky_night),
    FEW_CLOUDS_DAY(R.drawable.few_clouds_day),
    FEW_CLOUDS_NIGHT(R.drawable.few_clouds_night),
    SCATTERED_CLOUDS_DAY(R.drawable.scattered_clouds_day),
    SCATTERED_CLOUDS_NIGHT(R.drawable.scattered_clouds_night),
    BROKEN_CLOUDS_DAY(R.drawable.broken_clouds_day),
    BROKEN_CLOUDS_NIGHT(R.drawable.broken_clouds_night),
    SHOWER_RAIN_DAY(R.drawable.shower_rain_day),
    SHOWER_RAIN_NIGHT(R.drawable.shower_rain_night),
    RAIN_DAY(R.drawable.rain_day),
    RAIN_NIGHT(R.drawable.rain_night),
    THUNDERSTORM_DAY(R.drawable.thunderstorm_day),
    THUNDERSTORM_NIGHT(R.drawable.thunderstorm_night),
    SNOW_DAY(R.drawable.snow_day),
    SNOW_NIGHT(R.drawable.snow_night),
    MIST_DAY(R.drawable.mist_day),
    MIST_NIGHT(R.drawable.mist_night),
}

/**
 * Enum class representing different wind directions.
 *
 * Each enum constant represents a different wind direction and is associated with a string resource ID.
 */
enum class WindDirection(resId: Int) {
    NORTH(R.string.wind_north),
    NORTH_EAST(R.string.wind_north_east),
    EAST(R.string.wind_east),
    SOUTH_EAST(R.string.wind_south_east),
    SOUTH(R.string.wind_south),
    SOUTH_WEST(R.string.wind_south_west),
    WEST(R.string.wind_west),
    NORTH_WEST(R.string.wind_north_west),
}


enum class WeatherBackground {
    DAY,
    NIGHT
}
