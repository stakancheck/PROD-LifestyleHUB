/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.utils

sealed class ApiException(message: String) : Exception(message) {
    class UnauthorizedException(override val message: String = "User is not authorized") :
        ApiException(message)

    class NotFoundException(override val message: String = "Page not found") :
        ApiException(message)

    class ServerErrorException(override val message: String = "Server exception") :
        ApiException(message)

    class RequestTimeoutException(override val message: String = "Request timeout") :
        ApiException(message)
}
