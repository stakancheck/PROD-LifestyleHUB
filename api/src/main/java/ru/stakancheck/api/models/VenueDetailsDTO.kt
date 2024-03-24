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
data class VenueDetailsDTO(
    val response: VenueDetailsResponse
)

@Serializable
data class VenueDetailsResponse(
    val venue: VenueDetailsVenue
)

@Serializable
data class VenueDetailsVenue(
    val id: String,
    val name: String,
    val contact: VenueDetailsContact? = null,
    val location: VenueDetailsLocation,
    val categories: List<Category>,
    val url: String? = null,
    val likes: VenueDetailsLikes,
    val reasons: VenueDetailsReasons? = null,
    val description: String? = null,
    val shortUrl: String? = null,
    val photos: VenueDetailsPhotos? = null,
    val phrases: List<VenueDetailsPhrase>? = null,
    val hours: VenueDetailsHours? = null,
    val bestPhoto: Photo? = null,
)

@Serializable
data class VenueDetailsContact(
    val phone: String? = null,
    val formattedPhone: String? = null,
    val twitter: String? = null,
    val instagram: String? = null,
    val facebook: String? = null,
    val facebookUsername: String? = null,
    val facebookName: String? = null
)

@Serializable
data class VenueDetailsLocation(
    val lat: Double,
    val lng: Double,
    val formattedAddress: List<String>
)

@Serializable
data class VenueDetailsLikes(
    val count: Int
)

@Serializable
data class VenueDetailsReasons(
    val items: List<VenueDetailsReason>
)

@Serializable
data class VenueDetailsReason(
    val summary: String
)

@Serializable
data class VenueDetailsPhrase(
    val sample: VenueDetailsPhraseSample
)

@Serializable
data class VenueDetailsPhraseSample(
    val text: String
)

@Serializable
data class VenueDetailsHours(
    val status: String,
    val isOpen: Boolean,
    val timeframes: List<VenueDetailsTimeframe>
)

@Serializable
data class VenueDetailsTimeframe(
    val days: String,
    val includesToday: Boolean,
    val open: List<VenueDetailsOpen>
)

@Serializable
data class VenueDetailsOpen(
    val renderedTime: String
)

@Serializable
data class VenueDetailsPhotos(
    val groups: List<VenueDetailsPhotoGroup>
)

@Serializable
data class VenueDetailsPhotoGroup(
    val items: List<Photo>
)
