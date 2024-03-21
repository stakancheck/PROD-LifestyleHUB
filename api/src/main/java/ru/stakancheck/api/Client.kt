/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.stakancheck.api.utils.ApiException


fun HttpClient(): HttpClient = HttpClient(OkHttp) {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    expectSuccess = true
    engine {
        config {
            followRedirects(true)
        }
    }
    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            val clientException = exception as? ClientRequestException
                ?: return@handleResponseExceptionWithRequest
            val exceptionResponse = clientException.response

            when (exceptionResponse.status) {
                HttpStatusCode.Unauthorized -> {
                    throw ApiException.UnauthorizedException()
                }

                HttpStatusCode.NotFound -> {
                    throw ApiException.NotFoundException()
                }

                HttpStatusCode.RequestTimeout -> {
                    throw ApiException.RequestTimeoutException()
                }
            }
        }
    }
}
