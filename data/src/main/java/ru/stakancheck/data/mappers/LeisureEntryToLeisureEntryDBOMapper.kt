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
import ru.stakancheck.database.models.Interest
import ru.stakancheck.database.models.LeisureEntryDBO

internal class LeisureEntryToLeisureEntryDBOMapper {

    companion object {
        operator fun invoke(leisureEntry: LeisureEntry): LeisureEntryDBO {
            return LeisureEntryDBO(
                id = leisureEntry.id,
                title = leisureEntry.title,
                description = leisureEntry.description,
                date = leisureEntry.date,
                interest = leisureEntry.interestLink?.let { provideInterest(it) },
                ownerId = leisureEntry.ownerId
            )
        }

        private fun provideInterest(interestLink: InterestLink): Interest {
            return when (interestLink) {
                is InterestLink.Venue -> Interest(
                    id = interestLink.id,
                    type = "venue"
                )
            }
        }
    }
}