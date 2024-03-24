/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.models

import kotlinx.serialization.Serializable
import ru.stakancheck.api.models.common.Category
import ru.stakancheck.api.models.common.Photo


@Serializable
data class VenuesDTO(
    val response: Response
)

@Serializable
data class Response(
    val group: Group? = null,
)

@Serializable
data class Group(
    val results: List<Result>,
)

@Serializable
data class Result(
    val id: String,
    val venue: Venue,
    val photo: Photo? = null,
)


@Serializable
data class Venue(
    val id: String,
    val name: String,
    val location: Location,
    val categories: List<Category>,
)

@Serializable
data class Location(
    val distance: Int,
    val formattedAddress: List<String>,
)

