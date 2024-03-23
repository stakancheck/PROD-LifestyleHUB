/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.common.error

sealed interface DataError : Error {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        NO_INTERNET,
        UNAUTHORIZED,
        SERVER_ERROR,
        SERIALIZATION,
        LOCATION_PERMISSION_NOT_GRANTED,
        LOCATION_SERVICES_OFF,
        UNKNOWN,
    }

    enum class Location : DataError {
        NO_PERMISSION,
        LOCATION_SERVICES_OFF,
        UNKNOWN,
    }

    enum class Local : DataError {
        NO_DATA,
        NO_CACHE,
    }
}

fun DataError.Location.toNetworkError(): DataError.Network =
    when (this) {
        DataError.Location.NO_PERMISSION -> DataError.Network.LOCATION_PERMISSION_NOT_GRANTED
        DataError.Location.LOCATION_SERVICES_OFF -> DataError.Network.LOCATION_SERVICES_OFF
        DataError.Location.UNKNOWN -> DataError.Network.UNKNOWN
    }
