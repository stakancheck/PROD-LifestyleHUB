/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.data.api

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.URLProtocol
import io.ktor.http.headers
import io.ktor.http.parameters
import io.ktor.http.path
import ru.stakancheck.lifestylehub.BuildConfig
import ru.stakancheck.lifestylehub.data.models.ErrorResponse
import ru.stakancheck.lifestylehub.data.models.WeatherResponse

/**
 * WeatherApi is a class responsible for making API calls to the weather service.
 *
 * @property httpClient The HttpClient used to make the API calls.
 */
class WeatherApi(
    private val httpClient: HttpClient
) {
    /**
     * Fetches the weather data for the given latitude and longitude.
     *
     * @param lat The latitude for which to fetch the weather data.
     * @param long The longitude for which to fetch the weather data.
     * @return An ApiResult containing the WeatherResponse or an error.
     */
    suspend fun getWeather(lat: Double, long: Double): ApiResult<WeatherResponse> =
        ApiResult.withCatching<WeatherResponse, ErrorResponse> {
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
                    append("appid", API_KEY)
                }
            }
            Log.d(TAG, "getWeather: $httpResponse")
            httpResponse
        }

    companion object {
        /**
         * The tag used for logging.
         */
        const val TAG = "WeatherApi"

        /**
         * The host of the weather API.
         */
        const val HOST = "api.openweathermap.org"

        /**
         * The API key used to authenticate with the open weather API.
         */
        const val API_KEY = BuildConfig.OPEN_WEATHER_API_KEY
    }
}
