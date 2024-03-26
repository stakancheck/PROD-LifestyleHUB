/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.leisure.list.entities


/**
 * UI model for leisure entry editing.
 *
 * @param id - unique identifier of the entry.
 * @param title - title of the entry.
 * @param description - description of the entry.
 * @param formattedDate - formatted date of the entry.
 * @param date - date of the entry.
 * @param interestLink - link to the specific intest, e.g. [InterestLink.Venue]
 */
data class LeisureEntryEditUIModel(
    val id: Long,
    val title: String,
    val description: String,
    val date: Long,
    val interestLink: InterestLink?,
)