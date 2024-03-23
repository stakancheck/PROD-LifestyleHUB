/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.models

/**
 * Typealias for a list of [Interest] objects.
 */
typealias Interests = List<Interest>


/**
 * Sealed interface representing an Interest.
 * It can be used in different views and lists to present data of defferent interests.
 *
 * _Possible types:_
 * - [Venue]
 */
sealed interface Interest {
    /**
     * Data class representing a Venue.
     *
     * @property resultId The unique identifier for the result.
     * @property venueId The unique identifier for the venue.
     * @property name The name of the venue.
     * @property address The address of the venue.
     * @property distance The distance to the venue.
     * @property categories The list of categories of the venue list of [Category].
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
        val photoUrl: String?,
        val photoId: String?,
    ) : Interest {

        /**
         * Data class representing a Category of [Venue].
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

        override fun toString(): String {
            return "Venue -> name: $name, address: $address, distance: $distance"
        }
    }
}


