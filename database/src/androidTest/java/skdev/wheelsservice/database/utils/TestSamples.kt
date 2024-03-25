/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package skdev.wheelsservice.database.utils

import skdev.wheelsservice.database.models.Category
import skdev.wheelsservice.database.models.Contact
import skdev.wheelsservice.database.models.Hours
import skdev.wheelsservice.database.models.Location
import skdev.wheelsservice.database.models.TimeFrame
import skdev.wheelsservice.database.models.VenueDetailsDBO

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
}