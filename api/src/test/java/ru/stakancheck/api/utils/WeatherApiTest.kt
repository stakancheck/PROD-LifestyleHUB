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
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.stakancheck.api.WeatherApi
import ru.stakancheck.api.models.ApiResult
import ru.stakancheck.api.models.Language
import java.util.Properties


class WeatherApiTest {
    private val properties = Properties().apply {
        load(Thread.currentThread().contextClassLoader.getResourceAsStream("test.properties"))
    }

    @Test
    fun testGetCurrentWeather() {
        val apiResponse = """
            {
                "coord": {
                    "lon": 50.8079,
                    "lat": 61.6919
                },
                "weather": [
                    {
                        "id": 500,
                        "main": "Rain",
                        "description": "light rain",
                        "icon": "10n"
                    }
                ],
                "base": "stations",
                "main": {
                    "temp": 275.98,
                    "feels_like": 272.34,
                    "temp_min": 275.98,
                    "temp_max": 275.98,
                    "pressure": 1022,
                    "humidity": 30
                },
                "visibility": 10000,
                "wind": {
                    "speed": 4,
                    "deg": 280
                },
                "rain": {
                    "1h": 0.35
                },
                "clouds": {
                    "all": 0
                },
                "dt": 1710875946,
                "sys": {
                    "type": 1,
                    "id": 9036,
                    "country": "RU",
                    "sunrise": 1710816044,
                    "sunset": 1710859694
                },
                "timezone": 10800,
                "id": 485239,
                "name": "Syktyvkar",
                "cod": 200
            }

        """.trimIndent()

        val client = HttpClient(MockEngine) {
            engine {
                addHandler { request ->
                    respond(
                        content = apiResponse,
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
            }
        }

        val apiKey = properties.getProperty("OPEN_WEATHER_API_KEY")
        val weatherApi = WeatherApi(client, apiKey)

        runBlocking {
            val result = weatherApi.getCurrentWeather(50.8079, 61.6919, Language.RUSSIAN)
            assert(result is ApiResult.Success) { "Expected ApiResult.Success but got $result" }
        }
    }
}
