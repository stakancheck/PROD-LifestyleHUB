/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.common.error

import ru.stakancheck.common.R

fun Error.getTitleResourseId(): Int =
    when (this) {
        DataError.Network.REQUEST_TIMEOUT -> R.string.error_request_timeout
        DataError.Network.NO_INTERNET -> R.string.error_no_internet
        DataError.Network.UNAUTHORIZED -> R.string.error_unauthorized
        DataError.Network.SERVER_ERROR -> R.string.error_server_error
        DataError.Network.SERIALIZATION -> R.string.error_serialization
        DataError.Network.LOCATION_PERMISSION_NOT_GRANTED -> R.string.error_location_permission_not_granted
        DataError.Network.LOCATION_SERVICES_OFF -> R.string.error_location_services_off
        DataError.Network.UNKNOWN -> R.string.error_unknown
        DataError.Location.NO_PERMISSION -> R.string.error_location_permission_not_granted
        DataError.Location.LOCATION_SERVICES_OFF -> R.string.error_location_services_off
        DataError.Local.READ_ERROR -> R.string.error_read
        else -> R.string.error_unknown
    }

fun Error.getDescriptionResourseId(): Int? =
    when (this) {
        DataError.Network.REQUEST_TIMEOUT -> R.string.error_request_timeout_description
        DataError.Network.NO_INTERNET -> R.string.error_request_no_internet_description
        DataError.Network.UNAUTHORIZED -> R.string.error_unauthorized_description
        DataError.Network.SERVER_ERROR -> R.string.error_server_error_description
        DataError.Network.SERIALIZATION -> R.string.error_serialization_description
        DataError.Network.LOCATION_PERMISSION_NOT_GRANTED -> R.string.error_location_permission_not_granted_description
        DataError.Network.LOCATION_SERVICES_OFF -> R.string.error_location_services_off_description
        DataError.Network.UNKNOWN -> R.string.error_unknown_description
        DataError.Location.NO_PERMISSION -> R.string.error_location_permission_not_granted_description
        DataError.Location.LOCATION_SERVICES_OFF -> R.string.error_location_services_off_description
        DataError.Location.UNKNOWN -> R.string.error_unknown_description
        else -> null
    }
