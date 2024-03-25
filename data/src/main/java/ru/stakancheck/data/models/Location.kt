/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.models


/**
 * Time period for location to be relevant in milliseconds
 */
const val LOCATION_RELEVANT_PERIOD = 1_000 * 60

/**
 * Data class for location
 *
 * @property latitude Latitude of the location
 * @property longitude Longitude of the location
 * @property lastUpdate Last update time of the location
 */
data class Location(
    val latitude: Double,
    val longitude: Double,
    val lastUpdate: Long = System.currentTimeMillis()
) {
    override fun toString(): String {
        return "Location -> latitude: $latitude, longitude: $longitude"
    }
}

/**
 * Check if location is relevant
 */
fun Location?.checkRelevant(): Boolean = this?.lastUpdate?.let {
    System.currentTimeMillis() - it < LOCATION_RELEVANT_PERIOD
} ?: false
