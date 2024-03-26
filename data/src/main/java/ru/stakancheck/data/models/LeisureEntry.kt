/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.models

import java.util.Date


/**
 * Data class for leisure entry
 *
 * @param id - id of the entry
 * @param title - title of the entry
 * @param description - description of the entry
 * @param date - date of the entry
 * @param interestLink - link to the specific intest, e.g. [InterestLink.Venue]
 * @param ownerId - id of the owner (User)
 */
data class LeisureEntry(
    val id: Long? = null,
    val title: String,
    val description: String,
    val date: Date,
    val interestLink: InterestLink?,
    val ownerId: String,
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
