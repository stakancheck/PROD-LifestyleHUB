/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.models

import kotlinx.serialization.Serializable


/**
 * A data transfer object representing an error response from the OpenWeatherMap API.
 */
@Serializable
data class WeatherErrorDTO(
    val cod: String,
    override val message: String,
) : Exception()
