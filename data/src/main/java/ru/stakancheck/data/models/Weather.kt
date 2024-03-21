/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.models

import java.util.Date

/**
 * Data class representing weather.
 * [More abount weather condition](https://openweathermap.org/weather-conditions)
 *
 * @property weatherId The id of the weather condition.
 * @property weatherGroup The main group of weather parameters.
 * @property description The description of the weather condition.
 * @property icon The id of the weather icon.
 * @property temp The temperature.
 * @property feelsLike The perceived temperature.
 * @property pressure The atmospheric pressure at sea level.
 * @property humidity The humidity percentage.
 * @property tempMin The minimum temperature at the moment.
 * @property tempMax The maximum temperature at the moment.
 * @property windSpeed The wind speed in m/s.
 * @property windDeg The wind direction in degrees.
 * @property dt The date and time of the weather data getting.
 * @property sunrise The sunrise time.
 * @property sunset The sunset time.
 * @property location The name of the location.
 */
data class Weather(
    val weatherId: Int,
    val weatherGroup: String,
    val description: String,
    val icon: String,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    val tempMin: Double,
    val tempMax: Double,
    val windSpeed: Int,
    val windDeg: Int,
    val dt: Date,
    val sunrise: Date,
    val sunset: Date,
    val location: String,
)
