/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.stakancheck.api.utils.DateTimeUnixSerializer
import java.util.Date


/**
 * Data class representing a weather response.
 *
 * @property coord The coordinates of the location.
 * @property weather The list of weather conditions.
 * @property base The base parameter.
 * @property main The main weather parameters.
 * @property wind The wind parameters.
 * @property dt The date and time of the weather data.
 * @property sys The system parameters.
 * @property timezone The timezone of the location.
 * @property id The id of the location.
 * @property name The name of the location.
 * @property cod The cod parameter.
 */
@Serializable
data class CurrentWeatherDTO(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val wind: Wind,
    @Serializable(with = DateTimeUnixSerializer::class) val dt: Date,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

/**
 * Data class representing the coordinates of a location.
 *
 * @property lon The longitude of the location.
 * @property lat The latitude of the location.
 */
@Serializable
data class Coord(
    val lon: Double,
    val lat: Double
)

/**
 * Data class representing a weather condition.
 *
 * @property id The id of the weather condition.
 * @property main The main group of weather parameters.
 * @property description The description of the weather condition.
 * @property icon The id of the weather icon.
 */
@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

/**
 * Data class representing the main weather parameters.
 *
 * @property temp The temperature.
 * @property feelsLike The perceived temperature.
 * @property pressure The atmospheric pressure at sea level.
 * @property humidity The humidity percentage.
 * @property tempMin The minimum temperature at the moment.
 * @property tempMax The maximum temperature at the moment.
 */
@Serializable
data class Main(
    val temp: Double,
    @SerialName("feels_like") val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @SerialName("temp_min") val tempMin: Double,
    @SerialName("temp_max") val tempMax: Double
)

/**
 * Data class representing the wind parameters.
 *
 * @property speed The wind speed, in m/s.
 * @property deg The wind direction, in degrees.
 */
@Serializable
data class Wind(
    val speed: Double,
    val deg: Double
)

/**
 * Data class representing the system parameters.
 *
 * @property type An internal parameter.
 * @property id An internal parameter.
 * @property country The country code.
 * @property sunrise The sunrise time.
 * @property sunset The sunset time.
 */
@Serializable
data class Sys(
    val type: Int,
    val id: Int,
    val country: String,
    @Serializable(with = DateTimeUnixSerializer::class) val sunrise: Date,
    @Serializable(with = DateTimeUnixSerializer::class) val sunset: Date
)
