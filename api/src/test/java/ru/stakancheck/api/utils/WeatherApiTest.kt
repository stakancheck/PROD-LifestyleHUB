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
import org.junit.Test
import ru.stakancheck.api.WeatherApi
import ru.stakancheck.api.getResponseValidator
import ru.stakancheck.api.models.tools.ApiResult
import ru.stakancheck.api.models.tools.Language
import java.util.Properties


class WeatherApiTest {
    private val properties = Properties().apply {
        load(Thread.currentThread().contextClassLoader.getResourceAsStream("test.properties"))
    }

    private val apiKey = properties.getProperty("OPEN_WEATHER_API_KEY")

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
    fun `test get current weather 200`() {
        val apiResponse =
            Thread.currentThread().contextClassLoader.getResourceAsStream("get-weather-response.json")
                ?.bufferedReader()
                .use {
                    it?.readText()
                        ?: throw IllegalStateException("get-weather-response.json not found")
                }

        val weatherApi = WeatherApi(getClient(apiResponse, HttpStatusCode.OK), apiKey)

        runBlocking {
            val result = weatherApi.getCurrentWeather(50.8079, 61.6919, Language.RUSSIAN)
            assert(result is ApiResult.Success) { "Expected ApiResult.Success but got $result" }
        }
    }

    @Test
    fun `test get current weather unauthorized`() {
        val weatherApi = WeatherApi(
            getClient(
                """
                    {
                        "cod": 401,
                        "message": "Invalid API key. Please see https://openweathermap.org/faq#error401 for more info."
                    }
                """.trimIndent(),
                HttpStatusCode.Unauthorized
            ),
            apiKey
        )

        runBlocking {
            val result = weatherApi.getCurrentWeather(50.8079, 61.6919, Language.RUSSIAN)
            assert(result is ApiResult.Error && result.error is ApiException.UnauthorizedException) { "Expected ApiResult.Error but got $result" }
        }
    }

    @Test
    fun `test get current weather not found`() {
        val weatherApi = WeatherApi(getClient("", HttpStatusCode.NotFound), apiKey)

        runBlocking {
            val result = weatherApi.getCurrentWeather(50.8079, 61.6919, Language.RUSSIAN)
            assert(result is ApiResult.Error && result.error is ApiException.NotFoundException) { "Expected ApiResult.Error but got $result" }
        }
    }

    @Test
    fun `test get current weather timeout`() {
        val weatherApi = WeatherApi(getClient("", HttpStatusCode.RequestTimeout), apiKey)

        runBlocking {
            val result = weatherApi.getCurrentWeather(50.8079, 61.6919, Language.RUSSIAN)
            assert(result is ApiResult.Error && result.error is ApiException.RequestTimeoutException) { "Expected ApiResult.Error but got $result" }
        }
    }

    @Test
    fun `test get current weather exception 400`() {
        val weatherApi = WeatherApi(
            getClient(
                """
                    {
                        "cod": "400",
                        "message": "Nothing to geocode"
                    }
                """.trimIndent(),
                HttpStatusCode.BadRequest
            ),
            apiKey
        )

        runBlocking {
            val result = weatherApi.getCurrentWeather(50.8079, 61.6919, Language.RUSSIAN)
            assert(result is ApiResult.Error && result.error.message == "Nothing to geocode") { "Expected ApiResult.Error but got $result" }
        }
    }
}
