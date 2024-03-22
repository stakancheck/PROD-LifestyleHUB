/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.models

import kotlinx.serialization.Serializable


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

@Serializable
data class Category(
    val id: String,
    val shortName: String,
    val icon: Icon,
)

@Serializable
data class Icon(
    val prefix: String,
    val suffix: String,
)

@Serializable
data class Photo(
    val id: String,
    val prefix: String,
    val suffix: String,
)
