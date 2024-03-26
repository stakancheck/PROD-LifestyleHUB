/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.leisure.list.mappers

import ru.stakancheck.common.utils.DateTools
import ru.stakancheck.data.models.LeisureEntry
import ru.stakancheck.leisure.list.entities.InterestLink
import ru.stakancheck.leisure.list.entities.LeisureEntryUIModel
import ru.stakancheck.data.models.InterestLink as DataInterestLink

class LeisureEntryToUIModelMapper {
    companion object {
        operator fun invoke(leisureEntry: LeisureEntry): LeisureEntryUIModel {
            return LeisureEntryUIModel(
                id = leisureEntry.id ?: throw IllegalArgumentException("Leisure entry id is null"),
                title = leisureEntry.title,
                description = leisureEntry.description,
                formattedDate = DateTools.provideFormattedDate(leisureEntry.date),
                date = leisureEntry.date,
                interestLink = leisureEntry.interestLink?.let { provideInterestLink(it) }
            )
        }

        private fun provideInterestLink(interestLink: DataInterestLink): InterestLink {
            return when (interestLink) {
                is ru.stakancheck.data.models.InterestLink.Venue -> InterestLink.Venue(interestLink.id)
            }
        }
    }
}