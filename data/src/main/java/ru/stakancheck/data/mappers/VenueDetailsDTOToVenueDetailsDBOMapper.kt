/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.mappers

import ru.stakancheck.api.models.VenueDetailsDTO
import skdev.wheelsservice.database.models.Category
import skdev.wheelsservice.database.models.Contact
import skdev.wheelsservice.database.models.Hours
import skdev.wheelsservice.database.models.Location
import skdev.wheelsservice.database.models.TimeFrame
import skdev.wheelsservice.database.models.VenueDetailsDBO

internal class VenueDetailsDTOToVenueDetailsDBOMapper {
    companion object {
        operator fun invoke(dto: VenueDetailsDTO): VenueDetailsDBO {
            val venue = dto.response.venue
            return VenueDetailsDBO(
                id = venue.id,
                cachedAt = System.currentTimeMillis().toInt(),
                name = venue.name,
                contact = Contact(
                    phone = venue.contact?.phone,
                    formattedPhone = venue.contact?.formattedPhone,
                    twitter = venue.contact?.twitter,
                    instagram = venue.contact?.instagram,
                    facebook = venue.contact?.facebook,
                    facebookUsername = venue.contact?.facebookUsername,
                    facebookName = venue.contact?.facebookName
                ),
                location = Location(
                    lat = venue.location.lat,
                    lng = venue.location.lng,
                    formattedAddress = venue.location.formattedAddress
                ),
                categories = venue.categories.map {
                    Category(
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
                    Hours(
                        isOpen = it.isOpen,
                        status = it.status,
                        timeFrames = it.timeframes.map { timeframe ->
                            TimeFrame(
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
