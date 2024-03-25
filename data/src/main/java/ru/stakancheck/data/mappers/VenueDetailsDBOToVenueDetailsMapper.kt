/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.mappers

import ru.stakancheck.data.models.VenueDetails
import skdev.wheelsservice.database.models.VenueDetailsDBO

internal class VenueDetailsDBOToVenueDetailsMapper {
    companion object {
        operator fun invoke(dbo: VenueDetailsDBO): VenueDetails {
            return VenueDetails(
                id = dbo.id,
                name = dbo.name,
                contact = dbo.contact.let {
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
                    lat = dbo.location.lat,
                    lng = dbo.location.lng,
                    formattedAddress = dbo.location.formattedAddress
                ),
                categories = dbo.categories.map {
                    VenueDetails.Category(
                        id = it.id,
                        shortName = it.shortName,
                        iconUrl = it.iconUrl
                    )
                },
                url = dbo.url,
                likesCount = dbo.likesCount,
                description = dbo.description,
                shortUrl = dbo.shortUrl,
                bestPhotoUrl = dbo.bestPhotoUrl,
                photoUrls = dbo.photoUrls,
                phrases = dbo.phrases,
                reasons = dbo.reasons,
                hours = dbo.hours?.let {
                    VenueDetails.Hours(
                        isOpen = it.isOpen,
                        status = it.status,
                        timeFrames = it.timeFrames.map { timeframe ->
                            VenueDetails.Hours.TimeFrame(
                                period = timeframe.period,
                                times = timeframe.times
                            )
                        }
                    )
                }
            )
        }
    }
}
