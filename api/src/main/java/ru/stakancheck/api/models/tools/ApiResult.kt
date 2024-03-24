/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.models.tools

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess


/**
 * A sealed class representing the result of an API call.
 *
 * @param T The type of the data returned in case of success.
 */
sealed interface ApiResult<out T> {
    /**
     * Represents a successful API call.
     *
     * @property data The data returned by the API call.
     */
    data class Success<out T>(val data: T) : ApiResult<T>

    /**
     * Represents an unsuccessful API call.
     *
     * @property error The error message.
     * @property errorDescription A more detailed description of the error.
     */
    data class Error(val error: Throwable) : ApiResult<Nothing>

    companion object {

        /**
         * Executes the given [requestFunc] and wraps its result into an [ApiResult].
         *
         * @param R The type of the data returned in case of success.
         * @param E The type of the error response.
         * @param requestFunc The function to execute.
         * @return An [ApiResult] representing the result of the [requestFunc].
         */
        suspend inline fun <reified R, reified E : Throwable> withCatching(requestFunc: () -> HttpResponse): ApiResult<R> {
            return try {
                val response = requestFunc()
                if (response.status.isSuccess()) {
                    val resultResponse = response.body<R>()
                    Success(resultResponse)
                } else {
                    Error(response.body<E>())
                }
            } catch (e: ClientRequestException) {
                Error(e.response.body<E>())
            } catch (e: Throwable) {
                Error(e)
            }
        }
    }
}
