/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.mappers

import ru.stakancheck.data.models.Location
import android.location.Location as AndroidlLocation

class AndroidLocationToLocationMapper {
    companion object {
        operator fun invoke(androidLocation: AndroidlLocation): Location {
            return Location(
                latitude = androidLocation.latitude,
                longitude = androidLocation.longitude,
            )
        }
    }
}
