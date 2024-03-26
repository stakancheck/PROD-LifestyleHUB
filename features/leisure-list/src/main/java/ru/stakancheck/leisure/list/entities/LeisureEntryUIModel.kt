/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.leisure.list.entities


import java.util.Date


/**
 * UI model for leisure entry.
 *
 * @param id - unique identifier of the entry.
 * @param title - title of the entry.
 * @param description - description of the entry.
 * @param formattedDate - formatted date of the entry.
 * @param date - date of the entry.
 * @param interestLink - link to the specific intest, e.g. [InterestLink.Venue]
 */
data class LeisureEntryUIModel(
    val id: Long,
    val title: String,
    val description: String,
    val formattedDate: String,
    val date: Date,
    val interestLink: InterestLink?,
)

/**
 * Sealed class for interest link.
 * Uses for connecting leisure entry with specific interest object.
 *
 * In databse it serialize to string like this:
 * [InterestLink.Venue] -> venue_<id>
 */
sealed class InterestLink(open val id: String) {
    data class Venue(override val id: String) : InterestLink(id)
}
