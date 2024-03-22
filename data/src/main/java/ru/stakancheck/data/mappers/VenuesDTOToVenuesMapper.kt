/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.mappers

import ru.stakancheck.api.models.VenuesDTO
import ru.stakancheck.data.models.Category
import ru.stakancheck.data.models.Venue
import ru.stakancheck.data.models.Venues

class VenuesDTOToVenuesMapper {
    companion object {
        operator fun invoke(
            dto: VenuesDTO
        ): Venues {
            return dto.response.group.results.map { result ->
                result.venue.run {
                    Venue(
                        resultId = result.id,
                        venueId = id,
                        name = name,
                        address = location.formattedAddress,
                        distance = location.distance,
                        categories = categories.map { category ->
                            Category(
                                id = category.id,
                                shortName = category.shortName,
                                iconUrl = provideIconUrl(
                                    category.icon.prefix,
                                    category.icon.suffix
                                ),
                            )
                        },
                        photoUrl = providePhotoUrl(result.photo.prefix, result.photo.suffix),
                        photoId = result.photo.id
                    )
                }
            }
        }

        private fun provideIconUrl(prefix: String, postfix: String): String {
            return "${prefix}64$postfix"
        }

        private fun providePhotoUrl(prefix: String, postfix: String): String {
            return "${prefix}original$postfix"
        }
    }
}