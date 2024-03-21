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
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import ru.stakancheck.api.utils.ApiException
import ru.stakancheck.api.utils.LoggerBridge


fun HttpClient(
    loggerBridge: LoggerBridge
): HttpClient = HttpClient(OkHttp) {
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
    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                loggerBridge.log(tag = "Ktor", message)
            }
        }
        level = LogLevel.BODY
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
