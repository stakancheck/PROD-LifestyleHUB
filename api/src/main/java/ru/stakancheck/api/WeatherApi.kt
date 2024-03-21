/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import io.ktor.http.parameters
import io.ktor.http.path
import ru.stakancheck.api.models.ApiResult
import ru.stakancheck.api.models.CurrentWeatherDTO
import ru.stakancheck.api.models.Language
import ru.stakancheck.api.models.MeasurementUnit
import ru.stakancheck.api.models.WeatherErrorDTO

/**
 * WeatherApi is a class responsible for making API calls to the weather service.
 * [Api documentation](https://openweathermap.org)
 *
 * @property httpClient The HttpClient used to make the API calls.
 * @property apiKey The API key used to authenticate the requests.
 */
class WeatherApi(
    private val httpClient: HttpClient,
    private val apiKey: String
) {
    /**
     * Fetches the weather data for the given latitude and longitude.
     * [Api details](https://openweathermap.org/current)
     *
     * @param lat The latitude for which to fetch the weather data.
     * @param long The longitude for which to fetch the weather data.
     * @return An ApiResult containing the WeatherResponse or an error.
     */
    suspend fun getCurrentWeather(
        lat: Double,
        long: Double,
        lang: Language,
        units: MeasurementUnit = MeasurementUnit.METRIC,
    ): ApiResult<CurrentWeatherDTO> =
        ApiResult.withCatching<CurrentWeatherDTO, WeatherErrorDTO> {
            val httpResponse: HttpResponse = httpClient.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = HOST
                    path("data", "2.5", "weather")
                }

                headers {
                    append("Content-Type", "application/json")
                    append("Accept", "application/json")
                }

                parameters {
                    append("lat", lat.toString())
                    append("lon", long.toString())
                    append("lang", lang.code)
                    append("units", units.value)
                    append("appid", apiKey)
                }
            }
            httpResponse.body()
        }

    companion object {
        const val HOST = "api.openweathermap.org"
    }
}
