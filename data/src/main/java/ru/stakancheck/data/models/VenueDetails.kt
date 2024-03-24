/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.models


/**
 * Data class representing detailed information about a venue.
 *
 * @property id The unique identifier of the venue.
 * @property name The name of the venue.
 * @property contact The contact information of the venue.
 * @property location The location of the venue.
 * @property categories The categories the venue belongs to.
 * @property url The URL of the venue's website.
 * @property likesCount The number of likes the venue has received.
 * @property description A description of the venue.
 * @property shortUrl A shortened URL for the venue.
 * @property bestPhotoUrl The URL of the best photo of the venue.
 * @property photoUrls A list of URLs of photos of the venue.
 * @property phrases A list of phrases associated with the venue.
 * @property reasons A list of reasons for recommending the venue.
 * @property hours The opening hours of the venue.
 *
 * Also see [API docs](https://location.foursquare.com/developer/reference/get-venue-details) for more context.
 */
data class VenueDetails(
    val id: String,
    val name: String,
    val contact: Contact?,
    val location: Location,
    val categories: List<Category>,
    val url: String?,
    val likesCount: Int,
    val description: String?,
    val shortUrl: String?,
    val bestPhotoUrl: String?,
    val photoUrls: List<String>,
    val phrases: List<String>,
    val reasons: List<String>,
    val hours: String?
) {
    /**
     * Data class representing the contact information of a venue.
     *
     * @property phone The phone number of the venue.
     * @property formattedPhone The formatted phone number of the venue.
     * @property twitter The Twitter handle of the venue.
     * @property instagram The Instagram handle of the venue.
     * @property facebook The Facebook page ID of the venue.
     * @property facebookUsername The Facebook username of the venue.
     * @property facebookName The name of the venue on Facebook.
     */
    data class Contact(
        val phone: String?,
        val formattedPhone: String?,
        val twitter: String?,
        val instagram: String?,
        val facebook: String?,
        val facebookUsername: String?,
        val facebookName: String?
    )

    /**
     * Data class representing the location of a venue.
     *
     * @property lat The latitude of the venue.
     * @property lng The longitude of the venue.
     * @property formattedAddress The formatted address of the venue.
     */
    data class Location(
        val lat: Double,
        val lng: Double,
        val formattedAddress: List<String>
    )

    /**
     * Data class representing a category a venue belongs to.
     *
     * @property id The unique identifier of the category.
     * @property shortName The short name of the category.
     * @property iconUrl The URL of the icon of the category.
     */
    data class Category(
        val id: String,
        val shortName: String,
        val iconUrl: String
    )

    /**
     * Data class representing the opening hours of a venue.
     *
     * @property isOpen Whether the venue is currently open.
     * @property status The status of the venue's opening hours.
     * @property timeFrames The time frames of the venue's opening hours.
     */
    data class Hours(
        val isOpen: Boolean,
        val status: String,
        val timeFrames: List<TimeFrame>
    ) {
        /**
         * Data class representing a time frame of a venue's opening hours.
         *
         * @property period The period of the time frame.
         * @property times The times of the time frame.
         */
        data class TimeFrame(
            val period: String,
            val times: List<String>
        )
    }
}
