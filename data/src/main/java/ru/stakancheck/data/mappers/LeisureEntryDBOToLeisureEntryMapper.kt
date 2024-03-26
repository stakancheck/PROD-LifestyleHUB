/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.mappers

import ru.stakancheck.data.models.InterestLink
import ru.stakancheck.data.models.LeisureEntry
import ru.stakancheck.database.models.LeisureEntryDBO
import ru.stakancheck.database.models.Interest as InterestDBO

internal class LeisureEntryDBOToLeisureEntryMapper {
    companion object {
        operator fun invoke(dbo: LeisureEntryDBO): LeisureEntry {
            return LeisureEntry(
                id = dbo.id,
                title = dbo.title,
                description = dbo.description,
                date = dbo.date,
                interestLink = dbo.interest?.let { provideInterestLink(it) },
                ownerId = dbo.ownerId
            )
        }

        private fun provideInterestLink(interest: InterestDBO): InterestLink {
            return when (interest.type) {
                "venue" -> InterestLink.Venue(interest.id)
                else -> throw IllegalArgumentException("Unknown interest type: ${interest.type}")
            }
        }
    }
}
