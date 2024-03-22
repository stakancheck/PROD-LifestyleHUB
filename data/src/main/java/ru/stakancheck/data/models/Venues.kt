/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.models

/**
 * Typealias for a list of [Venue] objects.
 */
typealias Venues = List<Venue>

/**
 * Data class representing a Venue.
 *
 * @property resultId The unique identifier for the result.
 * @property venueId The unique identifier for the venue.
 * @property name The name of the venue.
 * @property address The address of the venue.
 * @property distance The distance to the venue.
 * @property categories The list of categories of the venue.
 * @property photoUrl The URL of the venue's photo in original size.
 * @property photoId The unique identifier for the venue's photo.
 */
data class Venue(
    val resultId: String,
    val venueId: String,
    val name: String,
    val address: String,
    val distance: Int,
    val categories: List<Category>,
    val photoUrl: String,
    val photoId: String,
)

/**
 * Data class representing a Category.
 *
 * @property id The unique identifier for the category.
 * @property shortName The short name of the category.
 * @property iconUrl The URL of the category's icon in size 64px.
 */
data class Category(
    val id: String,
    val shortName: String,
    val iconUrl: String,
)
