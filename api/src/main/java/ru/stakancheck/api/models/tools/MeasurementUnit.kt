/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.models.tools


/**
 * Enum class representing different measurement units.
 *
 * The measurement units are used to specify the unit of temperature in API calls.
 * [See for more details](https://openweathermap.org/current#data)
 *
 * - STANDARD: Represents temperature in Kelvin. This is the default unit, so no need to specify 'units' parameter in API call.
 * - METRIC: Represents temperature in Celsius. To use this, specify 'units=metric' in API call.
 * - IMPERIAL: Represents temperature in Fahrenheit. To use this, specify 'units=imperial' in API call.
 */
enum class MeasurementUnit(val value: String) {
    STANDARD("standard"),
    METRIC("metric"),
    IMPERIAL("imperial")
}
