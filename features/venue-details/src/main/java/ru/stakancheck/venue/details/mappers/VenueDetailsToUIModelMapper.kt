/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.venue.details.mappers

import ru.stakancheck.data.models.VenueDetails
import ru.stakancheck.venue.details.entities.VenueDetailsUIModel

class VenueDetailsToUIModelMapper {
    companion object {
        operator fun invoke(venueDetails: VenueDetails): VenueDetailsUIModel {
            return VenueDetailsUIModel(
                id = venueDetails.id,
                name = venueDetails.name,
                isOpen = venueDetails.hours?.isOpen,
                status = venueDetails.hours?.status,
                workHours = venueDetails.hours?.timeFrames?.map { it.toTimeFrameUIModel() }
                    ?: emptyList(),
                phone = venueDetails.contact?.phone?.let {
                    providePhoneUIModel(
                        it,
                        venueDetails.contact?.formattedPhone
                    )
                },
                url = venueDetails.url,
                location = venueDetails.location.toLocationUIModel(),
                contacts = venueDetails.contact?.let { provideContacts(it) } ?: emptyList(),
                categories = venueDetails.categories.map { it.toCategoryUIModel() },
                likesCount = venueDetails.likesCount,
                description = venueDetails.description,
                photoUrls = if (venueDetails.photoUrls.isEmpty()) {
                    venueDetails.bestPhotoUrl?.let {
                        listOf(it)
                    } ?: emptyList()
                } else {
                    venueDetails.photoUrls
                },
                phrases = venueDetails.phrases,
                reasons = venueDetails.reasons
            )
        }

        private fun VenueDetails.Location.toLocationUIModel(): VenueDetailsUIModel.Location =
            this.run {
                VenueDetailsUIModel.Location(
                    url = "geo:$lat,$lng",
                    address = formattedAddress.first()
                )
            }

        private fun VenueDetails.Category.toCategoryUIModel(): VenueDetailsUIModel.Category =
            this.run {
                VenueDetailsUIModel.Category(
                    id = id,
                    shortName = shortName,
                    iconUrl = iconUrl
                )
            }

        private fun VenueDetails.Hours.TimeFrame.toTimeFrameUIModel(): VenueDetailsUIModel.TimeFrame =
            this.run {
                VenueDetailsUIModel.TimeFrame(
                    period = period,
                    times = times
                )
            }

        private fun providePhoneUIModel(
            phone: String,
            formmatedPhone: String?
        ): VenueDetailsUIModel.Phone {
            return VenueDetailsUIModel.Phone("tel:$phone", formmatedPhone)
        }

        private fun provideContacts(contact: VenueDetails.Contact): List<VenueDetailsUIModel.Contact> {
            return mutableListOf<VenueDetailsUIModel.Contact>().apply {
                contact.twitter?.let { add(VenueDetailsUIModel.Contact.Twitter("https://twitter.com/$it")) }
                contact.facebookUsername?.let { add(VenueDetailsUIModel.Contact.Facebook("https://facebook.com/$it")) }
            }
        }
    }
}