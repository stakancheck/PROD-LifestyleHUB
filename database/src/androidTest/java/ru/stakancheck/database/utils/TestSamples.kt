/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.database.utils

import ru.stakancheck.database.models.Category
import ru.stakancheck.database.models.Contact
import ru.stakancheck.database.models.Hours
import ru.stakancheck.database.models.LeisureEntryDBO
import ru.stakancheck.database.models.Location
import ru.stakancheck.database.models.TimeFrame
import ru.stakancheck.database.models.VenueDetailsDBO
import java.util.Date

object TestSamples {
    val venueDetailsDBO1 = VenueDetailsDBO(
        id = "1",
        cachedAt = 1633027200,
        name = "Test Venue",
        contact = Contact(
            phone = "+0987654321",
            formattedPhone = "(987) 654-3210",
            twitter = "anothertestvenue",
            instagram = "anothertestvenue",
            facebook = "anothertestvenue",
            facebookUsername = "anothertestvenue",
            facebookName = "Another Test Venue"
        ),
        location = Location(
            lat = 40.748818,
            lng = -73.985429,
            formattedAddress = listOf("456 Another Test St", "Another Test City, NY 10002")
        ),
        categories = listOf(
            Category(
                id = "2",
                shortName = "Another Test",
                iconUrl = "https://example.com/anothericon.png"
            )
        ),
        url = "https://example.com",
        likesCount = 100,
        description = "This is a test venue.",
        shortUrl = "https://exmpl.co/testvenue",
        bestPhotoUrl = "https://example.com/photo.jpg",
        photoUrls = listOf(
            "https://example.com/photo1.jpg",
            "https://example.com/photo2.jpg",
            "https://example.com/photo3.jpg"
        ),
        phrases = listOf(
            "Test phrase 1",
            "Test phrase 2",
            "Test phrase 3"
        ),
        reasons = listOf(
            "Test reason 1",
            "Test reason 2",
            "Test reason 3"
        ),
        hours = Hours(
            isOpen = false,
            status = "Closed until 10:00 AM",
            timeFrames = listOf(
                TimeFrame(
                    period = "Sat–Sun",
                    times = listOf("10:00 AM–8:00 PM")
                )
            )
        )
    )

    val leisureEntryDBO1 = LeisureEntryDBO(
        title = "Test Title",
        description = "This is a test description.",
        date = Date(1633027200),
        interestLink = "venue_1231",
        ownerId = "1"
    )
}