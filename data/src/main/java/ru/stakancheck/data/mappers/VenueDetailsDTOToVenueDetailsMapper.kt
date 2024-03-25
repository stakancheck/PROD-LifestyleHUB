/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.mappers

import ru.stakancheck.api.models.VenueDetailsDTO
import ru.stakancheck.data.models.VenueDetails

internal class VenueDetailsDTOToVenueDetailsMapper {
    companion object {
        operator fun invoke(
            dto: VenueDetailsDTO
        ): VenueDetails {
            val venue = dto.response.venue
            return VenueDetails(
                id = venue.id,
                name = venue.name,
                contact = venue.contact?.let {
                    VenueDetails.Contact(
                        phone = it.phone,
                        formattedPhone = it.formattedPhone,
                        twitter = it.twitter,
                        instagram = it.instagram,
                        facebook = it.facebook,
                        facebookUsername = it.facebookUsername,
                        facebookName = it.facebookName
                    )
                },
                location = VenueDetails.Location(
                    lat = venue.location.lat,
                    lng = venue.location.lng,
                    formattedAddress = venue.location.formattedAddress
                ),
                categories = venue.categories.map {
                    VenueDetails.Category(
                        id = it.id,
                        shortName = it.shortName,
                        iconUrl = provideIconUrl(it.icon.prefix, it.icon.suffix)
                    )
                },
                url = venue.url,
                likesCount = venue.likes.count,
                description = venue.description,
                shortUrl = venue.shortUrl,
                bestPhotoUrl = venue.bestPhoto?.let { providePhotoUrl(it.prefix, it.suffix) },
                photoUrls = venue.photos?.groups?.flatMap { group ->
                    group.items.map {
                        providePhotoUrl(
                            it.prefix,
                            it.suffix
                        )
                    }
                } ?: emptyList(),
                phrases = venue.phrases?.map { it.sample.text } ?: emptyList(),
                reasons = venue.reasons?.items?.map { it.summary } ?: emptyList(),
                hours = venue.hours?.let {
                    VenueDetails.Hours(
                        isOpen = it.isOpen,
                        status = it.status,
                        timeFrames = it.timeframes.map { timeframe ->
                            VenueDetails.Hours.TimeFrame(
                                period = timeframe.days,
                                times = timeframe.open.map { it.renderedTime }
                            )
                        }
                    )
                }
            )
        }

        private fun provideIconUrl(prefix: String, postfix: String): String {
            return "${prefix}64$postfix"
        }

        private fun providePhotoUrl(prefix: String, postfix: String): String {
            return "${prefix}original$postfix"
        }
    }
}