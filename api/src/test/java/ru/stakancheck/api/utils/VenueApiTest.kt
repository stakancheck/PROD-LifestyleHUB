/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.stakancheck.api.VenueApi
import ru.stakancheck.api.getResponseValidator
import ru.stakancheck.api.models.tools.ApiResult
import ru.stakancheck.api.models.tools.Language
import java.io.File
import java.util.Properties

class VenueApiTest {
    private val properties = Properties().apply {
        load(Thread.currentThread().contextClassLoader.getResourceAsStream("test.properties"))
    }

    private val oauthToken = properties.getProperty("FORSQUARE_API_TOKEN")

    private fun getClient(response: String, code: HttpStatusCode): HttpClient {
        return HttpClient(MockEngine) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
            expectSuccess = true
            HttpResponseValidator(getResponseValidator())
            engine {
                addHandler {
                    respond(
                        content = response,
                        status = code,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
            }
        }
    }

    @Test
    fun `test get near venues 200`() {
        val venueApi = VenueApi(
            getClient(
                File("src/test/resources/gget-venue-response.json").readText(),
                HttpStatusCode.OK
            ),
            oauthToken
        )

        runBlocking {
            val result = venueApi.getNearVenues(50.8079, 61.6919, Language.RUSSIAN, 10, 0)
            assert(result is ApiResult.Success) { "Expected ApiResult.Success but got $result" }
        }
    }

    @Test
    fun `test get near venues unauthorized`() {
        val venueApi = VenueApi(
            getClient(
                """
                    {
                        "meta": {
                            "code": 401,
                            "errorType": "invalid_auth",
                            "errorDetail": "OAuth token invalid or revoked.",
                            "requestId": "65ff411ae189b91accd421c6"
                        },
                        "response": {}
                    }
                """.trimIndent(),
                HttpStatusCode.Unauthorized
            ),
            oauthToken
        )

        runBlocking {
            val result = venueApi.getNearVenues(50.8079, 61.6919, Language.RUSSIAN, 10, 0)
            assert(result is ApiResult.Error && result.error is ApiException.UnauthorizedException) { "Expected ApiResult.Error but got $result" }
        }
    }

    @Test
    fun `test get current near venues not found`() {
        val venueApi = VenueApi(getClient("", HttpStatusCode.NotFound), oauthToken)

        runBlocking {
            val result = venueApi.getNearVenues(50.8079, 61.6919, Language.RUSSIAN, 10, 0)
            assert(result is ApiResult.Error && result.error is ApiException.NotFoundException) { "Expected ApiResult.Error but got $result" }
        }
    }

    @Test
    fun `test get current near venues timeout`() {
        val venueApi = VenueApi(getClient("", HttpStatusCode.RequestTimeout), oauthToken)

        runBlocking {
            val result = venueApi.getNearVenues(50.8079, 61.6919, Language.RUSSIAN, 10, 0)
            assert(result is ApiResult.Error && result.error is ApiException.RequestTimeoutException) { "Expected ApiResult.Error but got $result" }
        }
    }

    @Test
    fun `test get near venues exception 400`() {
        val venueApi = VenueApi(
            getClient(
                """
                    {
                        "meta": {
                            "code": 400,
                            "errorType": "param_error",
                            "errorDetail": "ll must be of the form XX.XX,YY.YY (received 61.691891)",
                            "requestId": "65ff41015b934558675b6950"
                        },
                        "notifications": [
                            {
                                "type": "notificationTray",
                                "item": {
                                    "unreadCount": 0
                                }
                            }
                        ],
                        "response": {}
                    }
                """.trimIndent(),
                HttpStatusCode.BadRequest
            ),
            oauthToken
        )

        runBlocking {
            val result = venueApi.getNearVenues(50.8079, 61.6919, Language.RUSSIAN, 10, 0)
            assert(result is ApiResult.Error && result.error.message == "ll must be of the form XX.XX,YY.YY (received 61.691891)") { "Expected ApiResult.Error but got $result" }
        }
    }

    @Test
    fun `test get venue details big response 200`() {
        val venueApi = VenueApi(
            getClient(
                File("src/test/resources/get-venue-details-response.json").readText(),
                HttpStatusCode.OK
            ),
            oauthToken
        )

        runBlocking {
            val result = venueApi.getVenueDetails("5c5b0c940fe7a00033ddf78e", Language.ENGLISH)
            assert(result is ApiResult.Success) { "Expected ApiResult.Success but got $result" }

            val venueDetails = (result as ApiResult.Success).data.response.venue
            assertEquals("5c5b0c940fe7a00033ddf78e", venueDetails.id)
            assertEquals("Flower Child", venueDetails.name)
        }
    }

    @Test
    fun `test get venue details short response 200`() {
        val venueApi = VenueApi(
            getClient(
                File("src/test/resources/get-venue-details-response-min.json").readText(),
                HttpStatusCode.OK
            ),
            oauthToken
        )

        runBlocking {
            val result = venueApi.getVenueDetails("53a0bfb9498eea0ca3d84ee1", Language.ENGLISH)
            assert(result is ApiResult.Success) { "Expected ApiResult.Success but got $result" }

            val venueDetails = (result as ApiResult.Success).data.response.venue
            assertEquals("53a0bfb9498eea0ca3d84ee1", venueDetails.id)
            assertEquals("Магазин adidas", venueDetails.name)
        }
    }
}
