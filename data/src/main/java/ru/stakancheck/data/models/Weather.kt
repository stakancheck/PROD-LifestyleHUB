/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.models

import ru.stakancheck.api.models.Coord
import ru.stakancheck.api.models.Main
import ru.stakancheck.api.models.Sys
import ru.stakancheck.api.models.Weather
import java.util.Date

/**
 * Data class representing a weather response.
 *
 * @property weather The list of weather conditions.
 * @property main The main weather parameters.
 * @property dt The date and time of the weather data getting.
 * @property sys The system parameters.
 * @property timezone The timezone of the location.
 * @property id The id of the location.
 * @property name The name of the location.
 * @property cod The cod parameter.
 */
data class CurrentWeatherDTO(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val dt: Date,
    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

