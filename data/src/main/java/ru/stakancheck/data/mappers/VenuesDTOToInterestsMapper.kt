/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.mappers

import ru.stakancheck.api.models.VenuesDTO
import ru.stakancheck.data.models.Interest
import ru.stakancheck.data.models.Interests

class VenuesDTOToInterestsMapper {
    companion object {
        operator fun invoke(
            dto: VenuesDTO
        ): Interests {
            return dto.response.group?.results?.map { result ->
                result.venue.run {
                    Interest.Venue(
                        resultId = result.id,
                        venueId = id,
                        name = name,
                        address = location.formattedAddress.first(),
                        distance = location.distance,
                        categories = categories.map { category ->
                            Interest.Venue.Category(
                                id = category.id,
                                shortName = category.shortName,
                                iconUrl = provideIconUrl(
                                    category.icon.prefix,
                                    category.icon.suffix
                                ),
                            )
                        },
                        photoUrl = result.photo?.let { providePhotoUrl(it.prefix, it.suffix) },
                        photoId = result.photo?.id
                    )
                }
            } ?: emptyList()
        }

        private fun provideIconUrl(prefix: String, postfix: String): String {
            return "${prefix}64$postfix"
        }

        private fun providePhotoUrl(prefix: String, postfix: String): String {
            return "${prefix}original$postfix"
        }
    }
}