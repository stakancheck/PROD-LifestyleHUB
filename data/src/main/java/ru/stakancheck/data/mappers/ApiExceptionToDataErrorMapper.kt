/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data.mappers

import ru.stakancheck.api.utils.ApiException
import ru.stakancheck.common.error.DataError
import java.net.UnknownHostException

internal class ApiExceptionToDataErrorMapper {

    companion object {
        operator fun invoke(throwable: Throwable): DataError.Network {
            return when (throwable) {
                is ApiException.UnauthorizedException -> DataError.Network.UNAUTHORIZED
                is ApiException.NotFoundException, is ApiException.ServerErrorException -> DataError.Network.SERVER_ERROR
                is ApiException.RequestTimeoutException -> DataError.Network.REQUEST_TIMEOUT
                is UnknownHostException -> DataError.Network.NO_INTERNET
                else -> DataError.Network.UNKNOWN
            }
        }
    }
}
