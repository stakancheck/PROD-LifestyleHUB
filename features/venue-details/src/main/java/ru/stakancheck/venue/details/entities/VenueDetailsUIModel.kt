/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.venue.details.entities

/**
 * Data class representing detailed information about a venue.
 *
 * @property id The unique identifier of the venue.
 * @property name The name of the venue.
 * @property isOpen The open status of the venue.
 * @property status The status of the venue (like closed from yesterday).
 * @property workHours The opening hours of the venue.
 * @property phone The phone information of the venue.
 * @property email The email information of the venue.
 * @property webSite The website information of the venue.
 * @property contacts The contacts information of the venue.
 * @property location The location of the venue.
 * @property categories The categories the venue belongs to.
 * @property likesCount The number of likes the venue has received.
 * @property description A description of the venue.
 * @property photoUrls A list of URLs of photos of the venue.
 * @property phrases A list of phrases associated with the venue.
 * @property reasons A list of reasons for recommending the venue.
 *
 */
data class VenueDetailsUIModel(
    val id: String,
    val name: String,
    val isOpen: Boolean?,
    val status: String?,
    val workHours: List<TimeFrame>,
    val phone: Phone?,
    val url: String?,
    val contacts: List<Contact>,
    val location: Location,
    val categories: List<Category>,
    val likesCount: Int,
    val description: String?,
    val photoUrls: List<String>,
    val phrases: List<String>,
    val reasons: List<String>,
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

    /**
     * Data class representing the location of a venue.
     *
     * @property url The latitude, longitude in geo url.
     * @property address The formatted address of the venue.
     */
    data class Location(
        val url: String,
        val address: String
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
     * @property period The period (days).
     * @property times The time when open (like 7-evening).
     */
    data class TimeFrame(
        val period: String,
        val times: List<String>
    )

    /**
     * Data class representing the phone information of a venue.
     *
     * @property callUrl The url to call.
     * @property phone The formatted phone number of the venue.
     */
    data class Phone(val callUrl: String, val phone: String?)

    /**
     * Data class representing the contact information of a venue.
     *
     * @property url The URL of the contact.
     */
    sealed class Contact(open val url: String) {
        data class Facebook(override val url: String) : Contact(url)
        data class Twitter(override val url: String) : Contact(url)
    }
}
