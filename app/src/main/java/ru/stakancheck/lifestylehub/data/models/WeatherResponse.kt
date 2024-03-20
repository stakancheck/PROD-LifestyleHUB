/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherResponse(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Int,
    val wind: Wind,
    val rain: Rain,
    val clouds: Clouds,
    val dt: Long,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

@Serializable
data class Coord(
    val lon: Double, // Longitude of the location
    val lat: Double // Latitude of the location
)

@Serializable
data class Weather(
    val id: Int, // Weather condition id
    val main: String, // Group of weather parameters (Rain, Snow, Clouds etc.)
    val description: String, // Weather condition within the group
    val icon: String // Weather icon id
)

@Serializable
data class Main(
    val temp: Double, // Temperature
    val feels_like: Double, // Temperature accounting for the human perception of weather
    val pressure: Int, // Atmospheric pressure on the sea level, hPa
    val humidity: Int, // Humidity, %
    val temp_min: Double, // Minimum temperature at the moment
    val temp_max: Double, // Maximum temperature at the moment
    val sea_level: Int, // Atmospheric pressure on the sea level, hPa
    val grnd_level: Int // Atmospheric pressure on the ground level, hPa
)

@Serializable
data class Wind(
    val speed: Double, // Wind speed
    val deg: Int, // Wind direction, degrees (meteorological)
    val gust: Double // Wind gust
)

@Serializable
data class Rain(
    @SerialName("1h") val h1: Double, // Rain volume for the last 1 hour, mm
    @SerialName("3h") val h3: Double // Rain volume for the last 3 hours, mm
)

@Serializable
data class Clouds(
    val all: Int // Cloudiness, %
)

@Serializable
data class Sys(
    val type: Int, // Internal parameter
    val id: Int, // Internal parameter
    val country: String, // Country code (GB, JP etc.)
    val sunrise: Long, // Sunrise time, unix, UTC
    val sunset: Long // Sunset time, unix, UTC
)
